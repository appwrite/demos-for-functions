// Perform all your imports
const sdk = require("node-appwrite");

let TIME_LIMIT = process.env.DAYS_TO_EXPIRE || 5;
TIME_LIMIT *= 1000 * 60 * 60 * 24;

TIME_LIMIT = Date.now() - TIME_LIMIT;

// Initialise the client SDK
let client = new sdk.Client();
client
  .setEndpoint(process.env.API_ENDPOINT) // Your API Endpoint
  .setProject(process.env.APPWRITE_FUNCTION_PROJECT_ID) // Your project ID available by default
  .setKey(process.env.API_SECRET); // Your secret API key

// Initialise the database SDK
let database = new sdk.Database(client);

// Returns array of ids of all collections present.
async function getCollectionList() {
  /** @type {Array} */ let collections = await database.listCollections();
  collections = collections.collections;
  return collections.map((single) => single["$id"]);
}

// Returns ids of documents to delete
function getDocumentsToDelete(/** @type {Array} */ documents) {
  return documents.documents.map((document) => document["$id"]);
}

// Delete the documents based on `createdAt` field
async function pruneDocuments() {
  let deleteCount = 0;

  // Get collection list
  const collections = await getCollectionList();

  for (const collection of collections) {
    // Get ids of documents to delete
    /** @type {Array} */ const documents = getDocumentsToDelete(
      await database.listDocuments(collection, [`createdAt<=${TIME_LIMIT}`])
    );

    for (const document of documents) {
      // Delete the documents
      await database.deleteDocument(collection, document);
      deleteCount++;
    }
  }

  console.log("Total files deleted: %d", deleteCount);
}

(() => {
  pruneDocuments().catch((err) => {
    console.error("Error: ", err);
  });
})();
