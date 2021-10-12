import * as sdk from "https://deno.land/x/appwrite/mod.ts";

// Init SDK
const client: any = new sdk.Client();
client
    .setEndpoint(Deno.env.get("APPWRITE_ENDPOINT"))
    .setKey(Deno.env.get("APPWRITE_API_KEY"))
    .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID"))
;

const database: any = new sdk.Database(client);

// 157784760000 is 5 years in miliseconds
let fiveYearsAgo: number = Date.now() - 157784760000;

let getCollections: any = await database.listCollections();
let collections: Array<any> = getCollections.collections;

for (let collection of collections){

  let getDocuments: any = await database.listDocuments(
    collection["$id"], 
    [`createdAt<=${fiveYearsAgo}`],
  );
  let documents: Array<any> = getDocuments.documents;

  for (let document of documents){
    
    await database.deleteDocument(
      collection["$id"], 
      document["$id"],
    )
    
    console.log(`
      Document ${document["$id"]} in collection ${collection["$id"]} deleted
    `)
  }
}
