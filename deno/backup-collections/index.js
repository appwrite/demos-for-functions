import * as sdk from "https://deno.land/x/appwrite/mod.ts";
import { writeCSVObjects } from "https://deno.land/x/csv/mod.ts";
import { format } from "https://deno.land/std@0.110.0/datetime/mod.ts";
import { join } from "https://deno.land/std@0.110.0/path/mod.ts";

// Init SDK
let client = new sdk.Client();
client
  .setEndpoint(Deno.env.get("APPWRITE_ENDPOINT"))
  .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID")) // Your project ID available by default
  .setKey(Deno.env.get("APPWRITE_API_KEY")) // Your secret API key
;

// Init Apprwite DB
const db = new sdk.Database(client);

// Get the collections from the appwrite database
const promise = db.listCollections();

/**
 * Get the data for the collection from the appwrite server
 * @param {String} collectionId 
 * @returns {Object} data of the given id of the collection
 */

async function getDocuments(collectionId) {
  let docs;
  await db.listDocuments(collectionId).then(
    (data) => docs = data,
    (err) => {
      console.log(
        `Error while retreiving documents for collection-${collectionId}`,
        err,
      );
    },
  );
  return docs;
}

/**
 * Creates a CSV file for a collection and write data to the CSV file from respective collection  
 * @param {Object} data 
 * @param {String} filename 
 */

async function writeToCSV(data, filename) {
  const f = await Deno.open(filename, {
    write: true,
    create: true,
    truncate: true,
  });
  const asyncObject = async function* () {
    yield data;
  };
  await writeCSVObjects(f, asyncObject(), { header: Object.keys(data) });
  f.close();
}

/**
 * Upload the csv file to the appwrite storage
 * @param {String} filename 
 */

async function upload(filename) {
  const storage = new sdk.Storage(client);

  // Read a file
  const f = await Deno.readFile(filename);

  // Create file object to upload to storage
  const file = new File([f], filename);

  // Upload to appwrite storage
  await storage.createFile(file).then((res) => {
    console.log("successfull", res);
  }, (err) => {
    console.log("err", err);
  });
}

// Driver function for the appwrite function
promise.then(async ({ collections }) => {
  const date = format(new Date(), "dd-MM-yyyy");
  for (const collection of collections) {
    const collectionId = collection.$id;
    const docs = await getDocuments(collectionId);

    // File name according to the collectionId and date of backup
    const filename = join(Deno.cwd(), `collection-${collectionId}-${date}.csv`);
    await writeToCSV(docs, filename);
    await upload(filename);
  }
}, (err) => {
  console.log(err);
});
