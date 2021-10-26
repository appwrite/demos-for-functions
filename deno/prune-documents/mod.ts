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

const collections = [];
let offset = 0;
while (true){
  let response: any = await database.listCollections(
    undefined,
    10,
    offset
  );

  collections.push(...response.collections);

  if ((response.collections).length < 1){
    break
  }

  offset += 10;
}
offset = 0;

for (let collection of collections){
  let collectionId = collection["$id"];

  const documents = [];
  while (true){
    let response: any = await database.listDocuments(
      collectionId,
      [`createdAt<=${fiveYearsAgo}`],
      10,
      offset
    );

    documents.push(...response.documents);
    
    if ((response.documents).length < 1){
      break
    }

    offset += 10;
  }
  offset = 0;

  if (documents.length < 1){
    continue
  }

  for (let document of documents){
    console.log(await database.deleteDocument(
      collectionId, 
      document["$id"],
    ));
  }
}
