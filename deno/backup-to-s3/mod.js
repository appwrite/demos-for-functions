import * as sdk from "https://deno.land/x/appwrite@0.4.1/mod.ts";
import { S3Bucket } from "https://deno.land/x/s3@0.4.1/mod.ts";

// Client configs
const client = new sdk.Client();
client
    .setEndpoint(Deno.env.get('APPWRITE_ENDPOINT')) // Your API Endpoint
    .setProject(Deno.env.get('APPWRITE_FUNCTION_PROJECT_ID')) // Your project ID
    .setKey(Deno.env.get('APPWRITE_API_KEY')) // Your secret API key
;
const database = new sdk.Database(client);

const bucket = new S3Bucket({
  accessKeyID: Deno.env.get("AWS_ACCESS_KEY_ID"),
  secretKey: Deno.env.get("AWS_SECRET_ACCESS_KEY"),
  bucket: Deno.env.get("AWS_BUCKET"),
  region: Deno.env.get("AWS_REGION"),
});

// Constants
const encoder = new TextEncoder();
const date = Date.now();

const getAllCollectionDocuments = async (collectionId) => {
  const returnArr = [];
  
  do {  
    var documentsList = await database.listDocuments(collectionId, undefined, undefined, returnArr.length);
    // Add documents to return array
    returnArr.push(...documentsList.documents);
  } while (returnArr.length < documentsList.sum);

  return returnArr;
};

const getAllCollections = async () => {
  const returnArr = [];
  
  do {
    var collectionList = await database.listCollections(undefined, undefined, returnArr.length);
    // Add collections to return array
    returnArr.push(...collectionList.collections);
  } while (returnArr.length < collectionList.sum);

  return returnArr;
}

const convertCSV = (documents) => {
  let csv = '';
  const headers = Object.keys(documents[0]);
  csv += headers.join(',') + '\n';

  for (let i = 0; i < documents.length; i++) {
    const document = documents[i];

    const row = Object.values(document).map((value) => {
      // If value is an object, convert to JSON string 
      // else convert to string
      if (typeof value === 'object') {
        value = JSON.stringify(value)
      } else {
        value = value.toString()
      }

      // Parse special characters ( " and , )
      if (value.includes('"')) {
        value = `"${value.replace(/"/g, '""')}"`;
      }
      else if (value.includes(',')) {
        value = `"${value}"`;
      }


      return value;
    });

    csv += row.join(',') + '\n';
  }
  return csv;
}

const backupToS3 = async () => {
  try {
    const allCollections = await getAllCollections();

    for (let i = 0; i < allCollections.length; i++) {
      const collection = allCollections[i];

      const csvFileName = `${collection.name}-${date}.csv`;
      
      const documents = await getAllCollectionDocuments(collection["$id"]);
      if (documents.length > 0) {
        const csvData = convertCSV(documents);
        
        console.log(`Uploading ${csvFileName} to S3`);
        await bucket.putObject(csvFileName, encoder.encode(csvData), {
          contentType: 'text/csv',
        });
        console.log(`Uploaded ${csvFileName} to S3`);
      }
    }
  } catch (error) {
    console.log(error?.message);
  }
};

backupToS3();