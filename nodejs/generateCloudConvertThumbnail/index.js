// Perform all your imports
require("dotenv").config();
const sdk = require("node-appwrite");
const https = require("https");
const CloudConvert = require("cloudconvert");
const { createWriteStream, unlinkSync, createReadStream } = require("fs");
const { writeFile } = require("fs/promises");

// Initialise the AppWrite Client SDK
let client = new sdk.Client();
client
  .setEndpoint(process.env.APPWRITE_API_ENDPOINT) // Your API Endpoint
  .setProject(process.env.APPWRITE_FUNCTION_PROJECT_ID) // Your project ID available by default
  .setKey(process.env.APPWRITE_API_KEY); // Your secret API key

// Initialise the storage SDK
let storage = new sdk.Storage(client);

// Initialize the CloudConvert SDK
const cloudConvert = new CloudConvert(
  process.env.APPWRITE_CLOUD_CONVERT_API_KEY,
  true
);

const getInputData = async () => {
  return process.env.APPWRITE_FUNCTION_DATA.trim();
};

/**
 * @summary Task Builder
 * @description Builds and returns the job object required for thumbnail generation
 * @author Papu Kumar <papuruth@gmail.com>
 * @async
 * @returns {Promise} Returns the CloudConvert job object
 */
const taskBuilder = async () => {
  return cloudConvert.jobs.create({
    tasks: {
      "upload-my-file": {
        operation: "import/upload",
      },
      "generate-thumbnail": {
        operation: "thumbnail",
        output_format: "png",
        engine: "imagemagick",
        input: ["upload-my-file"],
        fit: "max",
        count: 1,
        width: process.env.THUMBNAIL_WIDTH || 400,
        height: process.env.THUMBNAIL_HEIGHT || 400,
      },
      "export-file": {
        operation: "export/url",
        input: ["generate-thumbnail"],
        inline: false,
        archive_multiple_files: false,
      },
    },
  });
};

const readFileFromStorage = async (fileId) => {
  const fileRes = await storage.getFileView(fileId);
  const fileMetaData = await storage.getFile(fileId);
  await writeFile(fileMetaData.name, fileRes);
  return { status: true, data: fileMetaData };
};

/**
 * @summary Upload To Cloud Convert
 * @description Uploads the file to CloudConvert for thumbnail creation
 * @author Papu Kumar <papuruth@gmail.com>
 * @param {object} jobs Cloud Convert Jobs object
 * @param {string} fileId AppWrite Storage File ID
 * @async
 */
const uploadToCloudConvert = async (jobs, fileMetaData) => {
  const uploadTask = jobs.tasks.filter(
    (task) => task.name === "upload-my-file"
  )[0];
  await cloudConvert.tasks.upload(
    uploadTask,
    createReadStream(fileMetaData.name),
    fileMetaData.name
  );
  unlinkSync(fileMetaData.name);
};

/**
 * @summary Handle CloudConvert Export
 * @description This function waits for the thumbnail creation operation to finish then
 * it exports the thumbnail and returns a url of the file, later the file is downloaded
 * using https module and written to a file
 * @author Papu Kumar <papuruth@gmail.com>
 * @param {object} jobs Cloud Convert Jobs object
 * @async
 * @returns {Promise} Returns a promise with either filename or error
 */
const handleExport = async (jobs) => {
  const job = await cloudConvert.jobs.wait(jobs.id);
  const exportTask = job.tasks.filter(
    (task) => task.operation === "export/url" && task.status === "finished"
  )[0];
  const file = exportTask.result.files[0];

  const writeStream = createWriteStream(file.filename);

  https.get(file.url, function (response) {
    response.pipe(writeStream);
  });

  return new Promise((resolve, reject) => {
    writeStream.on("finish", () => resolve(file.filename));
    writeStream.on("error", (err) => reject(err));
  });
};

/**
 * @summary Save File To Storage
 * @description Saves the file into AppWrite storage by reading the file written by CloudConvert export process
 * @author Papu Kumar <papuruth@gmail.com>
 * @param {string} filename The name of the file to be saved into storage
 * @async
 * @returns {Promise<string>} Storage id
 */
const saveFileToAppWriteStorage = async (filename) => {
  const res = await storage.createFile(createReadStream(filename));
  unlinkSync(filename);
  return res["$id"];
};

/**
 * @summary Main Function
 * @description This is the main function where the operation starts
 * @author Papu Kumar <papuruth@gmail.com>
 * @async
 */
const generateCloudConvertThumbnail = async () => {
  // Read the file id
  const fileId = await getInputData();
  try {
    if (fileId) {
      console.log("Thumbnail generation in progress. Please wait...");
      // Calls the reader to read file from sorage
      const res = await readFileFromStorage(fileId);
      if (res?.status) {
        // Calls the job builder function
        const jobs = await taskBuilder();
        // Calls the upload to cloud convert function which
        // uploads the file to cloud convert temporarily by reading from
        // Appwrite storage
        await uploadToCloudConvert(jobs, res?.data);
        // Call the export function where we wait for the job to finish
        // which returns a url we write the content in a file.
        const filename = await handleExport(jobs);
        console.log("Thumbnail generated. Please wait saving into storage...");
        // Calls save file to storage to save the file into Appwrite storage.
        const thumbnailId = await saveFileToAppWriteStorage(filename);
        console.log(`Thumbnail saved into storage with id: ${thumbnailId}`);
      }
    } else {
      throw Error("Please pass file ID");
    }
  } catch (error) {
    if (error?.code === 404) {
      console.log(`File not found with ID: ${fileId}`);
    } else {
      console.log(error);
    }
  }
};

generateCloudConvertThumbnail();
