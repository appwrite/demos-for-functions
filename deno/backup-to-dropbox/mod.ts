import * as sdk from "https://deno.land/x/appwrite/mod.ts";

// Init SDK
const client: any = new sdk.Client();
client
  .setEndpoint(Deno.env.get("APPWRITE_ENDPOINT"))
  .setKey(Deno.env.get("APPWRITE_API_KEY"))
  .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID"))
;

const accessToken = Deno.env.get("DROPBOX_ACCESS_TOKEN");
const database: any = new sdk.Database(client);

const collections = [];
let offset = 0;
while (true){
  let response: any = await database.listCollections(
    undefined,
    100,
    offset
  );

  collections.push(...response.collections);

  if ((response.collections).length < 1){
    break
  }

  offset += 100;
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
      100,
      offset
    );

    documents.push(...response.documents);
    
    if ((response.documents).length < 1){
      break
    }

    offset += 100;
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
const fileName = `${Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID")}-${date}.csv`;

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
  console.log(`Success backup collection to ${fileName}`);
} else {
  console.error(response['statusText'])
}
