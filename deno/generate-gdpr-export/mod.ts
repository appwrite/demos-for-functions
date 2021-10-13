import * as sdk from "https://deno.land/x/appwrite/mod.ts";
// import { writeCSV } from "https://deno.land/x/csv/mod.ts";

// Init SDK
const client: any = new sdk.Client();
client
  .setEndpoint(Deno.env.get("APPWRITE_ENDPOINT"))
  .setKey(Deno.env.get("APPWRITE_API_KEY"))
  .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID"))
;

const userId = `user:${Deno.env.get('APPWRITE_FUNCTION_DATA')}`;

const database: any = new sdk.Database(client);
const storage: any = new sdk.Storage(client);

const getCollections = async (): Promise<any> =>{
  let response: any = await database.listCollections();
  return response.collections;
} 

const getDocuments = async (collectionId: string, userId: string): Promise<any> =>{
  let output = "";
  let response: any = await database.listDocuments(collectionId);
  let documents: Array<any> = response.documents;
  for (let document of documents){

    if (document["$permissions"]["write"].find((user: string) => user == userId)){
      document["$permissions"] = JSON.stringify(document["$permissions"])
      
      for (let data of Object.values(document)){
        output += data + ",";
      }

      output += "\n"
    }
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
let part = 0;
const date = new Date().toISOString();
const collections: any = await getCollections();

for (let collection of collections){

  let collectionId = collection["$id"];
  let collectionName = collection["name"];

  let fileName = `${date}-${userId}-${collectionName}-${part}.csv`;

  data += await getDocumentsAttr(collectionId);
  data += "\n"
  data += await getDocuments(collectionId, userId);
  console.log(await storage.createFile(
      new File(
        [data],
        `${fileName}`
      ),
      ["*"],
      [userId],
  ));

  part += 1
  data = "";
}