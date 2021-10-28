import * as sdk from "https://deno.land/x/appwrite/mod.ts";
import { config } from "https://deno.land/x/dotenv@v3.0.0/mod.ts";

// Export dotenv file to Deno.env
config({ export: true });

// Init SDK
let client = new sdk.Client();
client
  .setEndpoint(Deno.env.get("APPWRITE_API_ENDPOINT")) // Your API Endpoint
  .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID")) // Your project ID
  .setKey(Deno.env.get("APPWRITE_API_KEY")); // Your secret API key

let storage = new sdk.Storage(client);

const headers = {
  Authorization: `Bearer ${Deno.env.get("CLOUD_CONVERT_API_KEY")}`,
  "Content-type": "application/json",
};

const fileId = Deno.env.get("APPWRITE_FUNCTION_DATA");
const parseRawTextResponse = async (rawResponse) => {
  return JSON.parse(await rawResponse.text());
};

/**
 * @summary Task Builder
 * @description Builds and returns the job object required for thumbnail generation
 * @author Papu Kumar <papuruth@gmail.com>
 * @async
 * @returns {Promise} Returns the CloudConvert job object
 */
const taskBuilder = async () => {
  const jobs = {
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
        width: Deno.env.get("THUMBNAIL_WIDTH") || 400,
        height: Deno.env.get("THUMBNAIL_HEIGHT") || 400,
      },
      "export-file": {
        operation: "export/url",
        input: ["generate-thumbnail"],
        inline: false,
        archive_multiple_files: false,
      },
    },
  };
  const rawResponse = await fetch("https://api.cloudconvert.com/v2/jobs", {
    method: "POST",
    headers: headers,
    body: JSON.stringify(jobs),
  });
  return parseRawTextResponse(rawResponse);
};

/**
 * @summary Create Form Data
 * @description Generates a formData by reading a file from AppWrite Storage
 * @param {object} uploadTask A cloud convert task
 * @author Papu Kumar <papuruth@gmail.com>
 * @async
 * @returns
 */
const createFormData = async (uploadTask) => {
  const formData = new FormData();

  for (const parameter in uploadTask.result.form.parameters) {
    formData.append(parameter, uploadTask.result.form.parameters[parameter]);
  }

  const fileMetadata = await storage.getFile(fileId);
  const file = await storage.getFileDownload(fileId);
  const reader = await file.blob();
  formData.append("file", reader, fileMetadata.name);
  return formData;
};

/**
 * @summary Upload To Cloud Convert
 * @description Uploads the file to CloudConvert for thumbnail creation
 * @author Papu Kumar <papuruth@gmail.com>
 * @param {object} jobs Cloud Convert Jobs object
 * @param {string} fileId AppWrite Storage File ID
 * @async
 */
const uploadToCloudConvert = async (jobs) => {
  const uploadTask = jobs.tasks.filter(
    (task) => task.name === "upload-my-file"
  )[0];

  const formData = await createFormData(uploadTask);
  await fetch(uploadTask.result.form.url, {
    method: "POST",
    body: formData,
  });
};

/**
 * @summary Jobs Waiter
 * @description Waits for all the jobs to finish and then returns the result
 * @param {string} jobId Main job id
 * @author Papu Kumar <papuruth@gmail.com>
 * @async
 * @returns {object} Returns the jobs object.
 */
const waitForJobToFinish = async (jobId) => {
  const response = await fetch(
    "https://api.cloudconvert.com/v2/jobs/" + jobId + "/wait",
    {
      headers,
    }
  );
  return parseRawTextResponse(response);
};

/**
 * @summary File downloader
 * @description Downloads the file url passed.
 * @param {string} url File url to be downloaded
 * @author Papu Kumar <papuruth@gmail.com>
 * @async
 * @returns
 */
const downloadOptimizedFile = async (url) => {
  const optimizedFileResponse = await fetch(url);
  return optimizedFileResponse.blob();
};

/**
 * @summary Handle CloudConvert Export
 * @description This function waits for the thumbnail creation operation to finish then
 * it exports the thumbnail and returns a url of the file, later the file is downloaded
 * and written to a file
 * @author Papu Kumar <papuruth@gmail.com>
 * @param {object} jobs Cloud Convert Jobs object
 * @async
 * @returns {Promise} Returns a promise with either filData or error
 */
const handleExport = async (jobs) => {
  const jobId = jobs?.id;
  const results = await waitForJobToFinish(jobId);
  const exportTask = results.data.tasks.filter(
    (task) => task.operation === "export/url" && task.status === "finished"
  )[0];
  const file = exportTask.result.files[0];
  const optimizedFileBlob = await downloadOptimizedFile(file?.url);
  return new File([optimizedFileBlob], file?.filename);
};

/**
 * @summary Save File To Storage
 * @description Saves the file into AppWrite storage
 * @author Papu Kumar <papuruth@gmail.com>
 * @param {string} fileData Contents of the file
 * @async
 * @returns {Promise<string>} Storage id
 */
const saveFileToAppWriteStorage = async (fileData) => {
  const res = await storage.createFile(fileData);
  return res["$id"];
};

/**
 * @summary Main Function
 * @description This is the main function where the operation starts
 * @author Papu Kumar <papuruth@gmail.com>
 * @async
 */
const generateCloudConvertThumbnail = async () => {
  try {
    if (fileId) {
      console.log("Thumbnail generation in progress. Please wait...");
      // Calls the reader to read file from sorage
      // Calls the job builder function
      const jobs = await taskBuilder();
      // Calls the upload to cloud convert function which
      // uploads the file to cloud convert temporarily by reading from
      // Appwrite storage
      await uploadToCloudConvert(jobs.data);
      // Call the export function where we wait for the job to finish
      // which returns a url we write the content in a file.
      const fileData = await handleExport(jobs.data);
      console.log("Thumbnail generated. Please wait saving into storage...");
      // Calls save file to storage to save the file into Appwrite storage.
      const thumbnailId = await saveFileToAppWriteStorage(fileData);
      console.log(`Thumbnail saved into storage with id: ${thumbnailId}`);
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
