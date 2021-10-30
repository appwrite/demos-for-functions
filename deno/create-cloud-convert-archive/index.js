import * as sdk from "https://deno.land/x/appwrite@0.4.1/mod.ts";
import { config } from "https://deno.land/x/dotenv@v3.0.0/mod.ts";

// Export dotenv file to Deno.env
config({ export: true });

const client = new sdk.Client();
const storage = new sdk.Storage(client);

client
  .setEndpoint(Deno.env.get("APPWRITE_API_ENDPOINT")) // Your API Endpoint
  .setProject(Deno.env.get("APPWRITE_PROJECT_ID")) // Your project ID
  .setKey(Deno.env.get("APPWRITE_API_KEY")); // Your secret API key

const { ids } = JSON.parse(Deno.env.get("APPWRITE_FUNCTION_DATA"));

const addUploadTasks = (jobs) => {
  for (let fileCount = 0; fileCount < ids.length; fileCount++) {
    const uploadTaskName = "upload-file-" + (ids.length - fileCount);
    jobs.tasks = {
      [uploadTaskName]: { operation: "import/upload" },
      ...jobs.tasks,
    };
    jobs.tasks["create-archive"].input.unshift(uploadTaskName);
  }
  return jobs;
};

const parseRawTextResponse = async (rawResponse) => {
  return JSON.parse(await rawResponse.text());
};

const postJobsToCloudConvert = async (jobs) => {
  const rawResponse = await fetch("https://api.cloudconvert.com/v2/jobs", {
    method: "POST",
    headers: {
      Authorization: "Bearer " + Deno.env.get("CLOUDCONVERT_API_KEY"),
      "Content-Type": "application/json",
    },
    body: JSON.stringify(jobs),
  });
  return await parseRawTextResponse(rawResponse);
};

const createFormData = async (uploadTask, idx) => {
  const formData = new FormData();

  for (const parameter in uploadTask.result.form.parameters) {
    formData.append(parameter, uploadTask.result.form.parameters[parameter]);
  }

  const fileMetadata = await storage.getFile(ids[idx]);
  const file = await storage.getFileDownload(ids[idx]);
  const reader = await file.blob();
  formData.append("file", reader, fileMetadata.name);
  return formData;
};

const createFormDataForUploadTasks = async (uploadTasks) => {
  const formDataArray = [];
  for (let idx = 0; idx < ids.length; idx++) {
    formDataArray.push(await createFormData(uploadTasks[idx], idx));
  }
  return formDataArray;
};

const uploadFormDataToCloudConvert = async (uploadTask, formData) => {
  await fetch(uploadTask.result.form.url, {
    method: "POST",
    body: formData,
  });
};

const uploadFormDataArrayToCloudConvert = async (
  uploadTasks,
  formDataArray
) => {
  for (let idx = 0; idx < ids.length; idx++) {
    await uploadFormDataToCloudConvert(uploadTasks[idx], formDataArray[idx]);
  }
};

const waitForJobToFinish = async (jobId) => {
  const response = await fetch(
    "https://api.cloudconvert.com/v2/jobs/" + jobId + "/wait",
    {
      headers: {
        Authorization: "Bearer " + Deno.env.get("CLOUDCONVERT_API_KEY"),
        "Content-Type": "application/json",
      },
    }
  );
  return parseRawTextResponse(response);
};

const downloadArchive = async (url) => {
  const response = await fetch(url);
  return await response.blob();
};

const jobs = {
  tasks: {
    "create-archive": {
      operation: "archive",
      output_format: "zip",
      engine: "archivetool",
      input: [],
    },
    "archive-url": {
      operation: "export/url",
      input: ["create-archive"],
      inline: false,
      archive_multiple_files: false,
    },
  },
};

addUploadTasks(jobs);

const response = await postJobsToCloudConvert(jobs);

const uploadTasks = response.data.tasks.filter(
  (task) => task.operation === "import/upload"
);

const jobId = response.data.id;
const formDataArray = await createFormDataForUploadTasks(uploadTasks);

await uploadFormDataArrayToCloudConvert(uploadTasks, formDataArray);
const result = await waitForJobToFinish(jobId);

const downloadTask = result.data.tasks.filter(
  (task) => task.operation === "export/url"
)[0];
const downloadUrl = downloadTask.result.files[0].url;
const filename = downloadTask.result.files[0].filename;

const archiveBlob = await downloadArchive(downloadUrl);
const archiveFile = new File([archiveBlob], filename);
console.log(await storage.createFile(archiveFile));
