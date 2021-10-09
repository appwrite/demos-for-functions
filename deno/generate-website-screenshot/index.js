// Perform all your imports
import * as sdk from "https://deno.land/x/appwrite/mod.ts";
import * as dotenv from "https://deno.land/x/dotenv/mod.ts";

const env = dotenv.config();

// Init SDK
let client = new sdk.Client();
client
  .setEndpoint(env.APPWRITE_ENDPOINT)
  .setProject(env.APPWRITE_PROJECT_ID) // Your project ID available by default
  .setKey(env.APPWRITE_API_KEY); // Your secret API key

// Initialise the storage SDK
const storage = new sdk.Storage(client);
const database = new sdk.Database(client);

//retrieve all collections
//for each collection, retrieve all documents and post to storage api

const documents = [];

async function fileBackup() {
  let retrievedCollections = await database.listCollections();

  for (let collection of retrievedCollections.collections) {
    let retrievedDocuments = await database.listDocuments(collection.$id);
    for (let document of retrievedDocuments.documents) {
      documents.push(document);
    }
  }

  const generatedCsv = generateCsv(documents);

  await Deno.writeFile("./data.csv", generatedCsv, function (err) {
    if (err) console.log(err);
  });

  await storage.createFile(
    new File(
      [Deno.readFile("./data.csv").toString()],
      `${new Date().toString()}.csv`
    )
  );

  console.log("backup created!");

  await Deno.remove("./data.csv");
}

async function generateCsv(items) {
  let csv = "";
  let keysCounter = 0;
  const keysAmount = Object.keys(items[0]).length;

  //generate headers for the csv
  for (let key in items[0]) {
    csv += key + (keysCounter + 1 < keysAmount ? "," : "\r\n");
    keysCounter++;
  }

  // generate data items for csv
  for (let row = 0; row < items.length; row++) {
    keysCounter = 0;

    for (let key in items[row]) {
      csv += items[row][key] + (keysCounter + 1 < keysAmount ? "," : "\r\n");
      keysCounter++;
    }
  }
  return csv;
}

fileBackup();
