// Perform all your imports
const sdk = require("node-appwrite");
require("dotenv").config();
const stringify = require("csv-stringify");
const { createWriteStream, createReadStream, unlinkSync } = require("fs");
const dropboxV2Api = require("dropbox-v2-api");

// Initialise the client SDK
let client = new sdk.Client();
const stringifier = stringify();
const {
  APPWRITE_API_ENDPOINT,
  APPWRITE_FUNCTION_PROJECT_ID,
  APPWRITE_API_KEY,
  DROPBOX_ACCESS_TOKEN,
} = process.env || {};

client
  .setEndpoint(APPWRITE_API_ENDPOINT) // Your API Endpoint
  .setProject(APPWRITE_FUNCTION_PROJECT_ID) // Your project ID available by default
  .setKey(APPWRITE_API_KEY); // Your secret API key

// Initialise the storage SDK
const storage = new sdk.Storage(client);
const collection = new sdk.Database(client);

const dropbox = dropboxV2Api.authenticate({
  token: DROPBOX_ACCESS_TOKEN,
});

const saveToDropBox = (fileName) => {
  try {
    const path = `/backups/${fileName.substring(2)}`;
    dropbox(
      {
        resource: "files/upload",
        parameters: {
          path,
        },
        readStream: createReadStream(fileName),
      },
      () => {
        console.log("Backup complete!!!");
        unlinkSync(fileName);
      }
    );
  } catch (error) {
    console.log(error?.message);
  }
};

const createCSV = (data) => {
  const filename = `./new-backup-${Date.now()}.csv`;
  const writer = createWriteStream(filename);
  stringifier.pipe(writer);
  writer.on("error", function (err) {
    console.error(err.message);
  });
  writer.on("finish", async function () {
    saveToDropBox(filename);
  });
  data.map((item) => {
    stringifier.write([item]);
  });
  stringifier.end();
};

const getAllDataFromCollections = async (collections) => {
  let documentsList = await Promise.all(
    collections.map(async (item) => {
      const data = await collection.listDocuments(item["$id"]);
      return data?.documents;
    })
  );
  documentsList = documentsList.flatMap((item) => item);
  if (documentsList?.length > 0) {
    return Promise.all(
      documentsList.map(async (doc) => {
        return collection.getDocument(doc["$collection"], doc["$id"]);
      })
    );
  }
};
const backupToDropBox = async () => {
  try {
    const collectionList = await collection.listCollections();
    if (collectionList?.sum > 0) {
      const data = await getAllDataFromCollections(collectionList?.collections);
      if (data.length) {
        createCSV(data);
      }
    }
  } catch (error) {
    console.log(error?.message);
  }
};

backupToDropBox();
