import * as sdk from "https://deno.land/x/appwrite/mod.ts";

// Init SDK
const client: any = new sdk.Client();
client
  .setEndpoint(Deno.env.get("APPWRITE_ENDPOINT"))
  .setKey(Deno.env.get("APPWRITE_API_KEY"))
  .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID"))
;

const userId = Deno.env.get("APPWRITE_FUNCTION_USER_ID");
const database: any = new sdk.Database(client);
const storage: any = new sdk.Storage(client);

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

let data = "";
for (let collection of collections){
  let collectionId = collection["$id"];
  let collectionName = collection["name"];

  const documents = [];
  while (true){
    let response: any = await database.listDocuments(
      collectionId,
      undefined,
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

  for (let attr of Object.keys(documents[0])){
    data += `${collectionName}.${attr},`;
  }
  data += "\n"

  for (let document of documents){
    if (!document["$permissions"]["write"].find((user: string) => user == userId || user == "*")){
      continue
    }

    // if this doesnt use json.stringify the value will be: [object object]
    // and the comma replaced to dot because the value will be splited in csv
    document["$permissions"] = JSON.stringify(document["$permissions"]).replace(',', '.')
    
    for (let item of Object.values(document)){
      data += item + ",";
    }

    data += "\n"
  }
  data += "\n"
}

const date = Date.now();
const fileName = `${Deno.env.get("APPWRITE_FUNCTION_USER_ID")}-${date}.csv`;

console.log(await storage.createFile(
  new File(
    [data],
    `${fileName}`
  ),
  ["*"],
  [userId],
));
