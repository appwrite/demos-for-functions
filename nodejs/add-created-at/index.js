// Perform all your imports
const sdk = require("node-appwrite");

// Initialise the client SDK
let client = new sdk.Client();

client
  .setEndpoint(process.env.APPWRITE_ENDPOINT) // Your API Endpoint
  .setProject(process.env.APPWRITE_PROJECT_ID) // Your project ID
  .setKey(process.env.APPWRITE_API_KEY); // Your secret API key

const payload = JSON.parse(process.env.APPWRITE_FUNCTION_EVENT_DATA);
const { $id, $collection } = payload;

if (!payload.hasOwnProperty("createdAt")) return console.error("Rule for createdAt has not been created in the collection!");

const database = new sdk.Database(client);

database
  .updateDocument($collection, $id, {
    createdAt: Math.floor(Date.now() / 1000),
  })
  .then(console.log)
  .catch(console.error);
