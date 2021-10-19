import * as sdk from "https://deno.land/x/appwrite/mod.ts";

// Init SDK
const client: any = new sdk.Client();
client
  .setEndpoint(Deno.env.get("APPWRITE_ENDPOINT"))
  .setKey(Deno.env.get("APPWRITE_API_KEY"))
  .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID"))
;

const database: any = new sdk.Database(client);
const accessToken = Deno.env.get("DROPBOX_ACCESS_TOKEN");

const getCollections = async (): Promise<any> =>{
  let response: any = await database.listCollections();
  return response.collections;
} 

const getDocuments = async (collectionId: string): Promise<any> =>{
  let output = "";
  let response: any = await database.listDocuments(collectionId);
  let documents: Array<any> = response.documents;
  for (let document of documents){
      // if this doesnt use json.stringify the value will be: [object object]
      // and the comma replaced to dot because the value will be splited in csv
      document["$permissions"] = JSON.stringify(document["$permissions"]).replace(',', '.')
      
      for (let data of Object.values(document)){
        output += data + ",";
      }

      output += "\n"
  }
  return output;
} 

const getDocumentsAttr = async (collectionId: string): Promise<any> => {
  let output = "";
  let response: any = await database.listDocuments(collectionId);
  let document: Array<any> = response.documents[0];
  for (let attr of Object.keys(document)){
    output += attr + ",";
  }
  return output
} 

let data = "";
const date = Date.now();
const collections: any = await getCollections();

for (let collection of collections){

  let collectionId = collection["$id"];
  let collectionName = collection["name"];

  let fileName = `${collectionName}-${date}.csv`;

  data += await getDocumentsAttr(collectionId);
  data += "\n"
  data += await getDocuments(collectionId);

  const response = await fetch(
    "https://content.dropboxapi.com/2/files/upload",
    {
      method: "POST",
      headers: {
        "Authorization":
          `Bearer ${accessToken}`,
        "Dropbox-API-Arg":
          `{"path": "/backup/${fileName}","mode": "add","autorename": true,"mute": false,"strict_conflict": false}`,
        "Content-Type": "application/octet-stream",
      },
      body: new TextEncoder().encode(data),
    },
  );

  if (response['status'] === 200){
    console.log(`Success backup collection ${collectionId} to ${fileName}`);
  } else {
    console.log(response['statusText'])
  }

  data = "";
}
