import * as sdk from "https://deno.land/x/appwrite/mod.ts";

// Init SDK
let client = new sdk.Client();
client
  .setEndpoint(Deno.env.get("APPWRITE_ENDPOINT")) // Your API Endpoint
  .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID")) // Your project ID available by default
  .setKey(Deno.env.get("APPWRITE_API_KEY")); // Your secret API key

let storage = new sdk.Storage(client);

(async () => {
  let list = await storage.listFiles("", 100, 0, "DESC");
  let days = parseFloat(Deno.env.get("DAYS_TO_EXPIRE"));
  let timestamp = new Date();
  timestamp = timestamp.setDate(timestamp.getDate() - days);
  let filesToDelete = 0;

  for (const file of list.files) {
    if (file.dateCreated * 1000 < timestamp) {
      filesToDelete++;
      await storage.deleteFile(file.id);
      console.log(`Deleted file ${file.id}`);
    }
  }

  console.log(`${filesToDelete} files deleted`);
})();
