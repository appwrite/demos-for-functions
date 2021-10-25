import * as sdk from "https://deno.land/x/appwrite/mod.ts";

// Init SDK
const client = new sdk.Client();
client
  .setEndpoint(Deno.env.get("APPWRITE_ENDPOINT"))
  .setKey(Deno.env.get("APPWRITE_API_KEY"))
  .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID"))
;

const { $id, $collection } = JSON.parse(Deno.env.get("APPWRITE_FUNCTION_EVENT_DATA"));

const database = new sdk.Database(client);
let date = new Date().toISOString();
let promise = database.updateDocument($collection, $id, { 'updatedAt': date });
promise
  .then(console.log)
  .catch(console.error)