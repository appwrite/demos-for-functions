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

// Data
const payload = JSON.parse(Deno.env.get("APPWRITE_FUNCTION_EVENT_DATA"));
const uri = payload["url"];
const format = payload["format"];

async function generateWebsiteScreenshot(url, format = "jpg") {
  const file = await createExportTask(await captureScreenshot(url, format));

  const response = await fetch(file.url);

  const blob = await response.blob();
  const buffer = await blob.arrayBuffer();
  const unit8arr = new Deno.Buffer(buffer).bytes();

  await Deno.writeFile(file.filename, unit8arr);

  try {
    const storedFile = await storage.createFile(
      new File(await Deno.readFile(`./${file.filename}`), file.filename)
    );

    console.log(`Stored screenshot with id: ${storedFile["$id"]}`);
  } catch (error) {
    console.error(error);
  }

  Deno.remove(file.filename);
}

async function captureScreenshot(url, format) {
  const config = JSON.stringify({ url, output_format: format });

  // Create a request to capture the website screen shot

  const captureRequest = await fetch(
    "https://api.cloudconvert.com/v2/capture-website",
    {
      method: "POST",
      headers: {
        Authorization: `Bearer ${env.CLOUDCONVERT_API_KEY}`,
        "Content-Type": "application/json",
      },
      body: config,
    }
  );

  const captureRequestData = (await captureRequest.json()).data;

  // Wait for the task to finish

  await fetch(
    `https://api.cloudconvert.com/v2/tasks/${captureRequestData.id}/wait`,
    {
      headers: {
        Authorization: `Bearer ${env.CLOUDCONVERT_API_KEY}`,
      },
    }
  );

  return captureRequestData.id;
}

async function createExportTask(id) {
  // Create a request to export the screenshot

  const createExportRequest = await fetch(
    `https://api.cloudconvert.com/v2/export/url?input=${id}`,
    {
      method: "POST",
      headers: {
        Authorization: `Bearer ${env.CLOUDCONVERT_API_KEY}`,
      },
    }
  );

  const createExportRequestData = (await createExportRequest.json()).data;

  // Wait for it to get exported

  await fetch(
    `https://api.cloudconvert.com/v2/tasks/${createExportRequestData.id}/wait`,
    {
      headers: {
        Authorization: `Bearer ${env.CLOUDCONVERT_API_KEY}`,
      },
    }
  );

  const exportDataRequest = await fetch(
    `https://api.cloudconvert.com/v2/tasks/${createExportRequestData.id}}`,
    {
      headers: {
        Authorization: `Bearer ${env.CLOUDCONVERT_API_KEY}`,
      },
    }
  );

  const exportData = (await exportDataRequest.json()).data;

  return {
    url: exportData.result.files[0].url,
    filename: exportData.result.files[0].filename,
  };
}

generateWebsiteScreenshot(uri, format);
