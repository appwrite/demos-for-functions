import * as sdk from "https://deno.land/x/appwrite/mod.ts";
import * as base64 from "https://denopkg.com/chiefbiiko/base64/mod.ts";

// Get parameters
const imageUrl = Deno.env.get("APPWRITE_FUNCTION_DATA"),
  TINYPNG_API_KEY = Deno.env.get("TINYPNG_API_KEY"),
  TINYPNG_ENDPOINT = "https://api.tinify.com/shrink";

// Init SDK
let client = new sdk.Client();
client
  .setEndpoint(Deno.env.get("API_ENDPOINT"))
  .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID")) // Your project ID available by default
  .setKey(Deno.env.get("API_SECRET")); // Your secret API key

// Init storage
let storage = new sdk.Storage(client);

// Upload image to Tinypng with URL and download and save to Storage
async function compressTinypngImage(imageUrl) {
  // Upload image with url
  try {
    const image = await fetch(TINYPNG_ENDPOINT, {
      method: "POST",
      headers: {
        Authorization: `Basic ${base64.fromUint8Array(
          new TextEncoder().encode(`api:${TINYPNG_API_KEY}`)
        )}`,
        "Content-type": "application/json",
      },
      body: JSON.stringify({
        source: {
          url: imageUrl,
        },
      }),
    }).then((res) => res.json());

    // Downloaded the generated file
    const fileUrl = image.output.url;
    const blob = await fetch(fileUrl).then((file) => file.blob());

    // Blob to File
    const compressedImage = new File([blob], Date.now(), {
      lastModified: Date.now(),
    });

    // Store into Appwrite Storage
    const test = await storage.createFile(compressedImage);
    return test["$id"];
  } catch (err) {
    console.error(err);
    return;
  }
}

async function run() {
  const id = await compressTinypngImage(imageUrl);
  if (id) console.log(JSON.stringify({ fileId: id }));
}

try {
  run();
} catch (err) {
  console.error(err);
}
