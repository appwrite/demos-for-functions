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
    jobs.tasks["merge-files"].input.push(uploadTaskName);
  }
  return jobs;
};

const jobs = {
  tasks: {
    "merge-files": {
      operation: "merge",
      output_format: "pdf",
      engine: "poppler",
      input: [],
    },
    "merged-files-url": {
      operation: "export/url",
      input: ["merge-files"],
      inline: false,
      archive_multiple_files: false,
    },
  },
};

addUploadTasks(jobs);
console.log(jobs);
