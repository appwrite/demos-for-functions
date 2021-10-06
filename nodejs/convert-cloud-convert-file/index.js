const sdk = require("node-appwrite");
const CloudConvert = require("cloudconvert");
const fs = require("fs");
const https = require("https");

require("dotenv").config();

const client = new sdk.Client();
const storage = new sdk.Storage(client);
const cloudConvert = new CloudConvert(process.env.CLOUD_CONVERT_API_KEY, true);

client
  .setEndpoint(process.env.APPWRITE_API_ENDPOINT) // Your API Endpoint
  .setProject(process.env.APPWRITE_PROJECT_ID) // Your project ID
  .setKey(process.env.APPWRITE_API_KEY); // Your secret API key

const createCloudConvertJob = async () => {
  let job = await cloudConvert.jobs.create({
    tasks: {
      "upload-file": {
        operation: "import/upload",
      },
      "convert-file": {
        operation: "convert",
        input: ["upload-file"],
        output_format: "jpg",
      },
      "export-file": {
        operation: "export/url",
        input: ["convert-file"],
        inline: false,
        archive_multiple_files: true,
      },
    },
  });
  const uploadTask = job.tasks.filter((task) => task.name === "upload-file")[0];
  const inputFile = await fs.createReadStream("./Coding.png");
  const res = await cloudConvert.tasks.upload(
    uploadTask,
    inputFile,
    "Coding.png"
  );
  job = await cloudConvert.jobs.wait(job.id); // Wait for job completion
  const exportTask = job.tasks.filter(
    (task) => task.operation === "export/url" && task.status === "finished"
  )[0];
  const file = exportTask.result.files[0];

  const writeStream = fs.createWriteStream("./" + file.filename);
  let test;
  https.get(file.url, function (response) {
    test = response;
    response.pipe(writeStream);
  });
};

createCloudConvertJob();
