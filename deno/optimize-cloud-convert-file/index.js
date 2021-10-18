import * as sdk from "https://deno.land/x/appwrite@0.4.0/mod.ts";
import { config } from "https://deno.land/x/dotenv@v3.0.0/mod.ts";

// Export dotenv file to Deno.env
config({ export: true });

const client = new sdk.Client();
const storage = new sdk.Storage(client);

client
  .setEndpoint(Deno.env.get("APPWRITE_API_ENDPOINT")) // Your API Endpoint
  .setProject(Deno.env.get("APPWRITE_PROJECT_ID")) // Your project ID
  .setKey(Deno.env.get("APPWRITE_API_KEY")); // Your secret API key

const jobs = {
  tasks: {
    "upload-file": {
      operation: "import/upload",
    },
    "optimize-file": {
      operation: "optimize",
      input: ["upload-file"],
    },
    "optimized-file-url": {
      operation: "export/url",
      input: ["optimize-file"],
      inline: false,
      archive_multiple_files: false,
    },
  },
};

const rawResponse = await fetch("https://api.cloudconvert.com/v2/jobs", {
  method: "POST",
  headers: {
    Authorization: "Bearer " + Deno.env.get("CLOUDCONVERT_API_KEY"),
    "Content-Type": "application/json",
  },
  body: JSON.stringify(jobs),
});

const response = JSON.parse(await rawResponse.text());

const formData = new FormData();

const uploadTask = response.data.tasks[0];
for (const parameter in uploadTask.result.form.parameters) {
  formData.append(parameter, uploadTask.result.form.parameters[parameter]);
}
