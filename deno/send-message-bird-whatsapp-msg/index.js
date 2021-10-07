import * as sdk from "https://deno.land/x/appwrite/mod.ts";
import initMB from "https://esm.sh/messagebird@3.7.1";

import "https://deno.land/x/dotenv/load.ts";

// Init SDK
let client = new sdk.Client();
client
  .setEndpoint(Deno.env.get("APPWRITE_ENDPOINT"))
  .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID")) // Your project ID available by default
  .setKey(Deno.env.get("APPWRITE_API_KEY")); // Your secret API key

const {
  MESSAGE_BIRD_API_KEY,
  WHATSAPP_CHANNEL_ID,
  PHONE_NUMBER,
  TEXT,
  REPORT_URL,
} = JSON.parse(Deno.env.get("APPWRITE_FUNCTION_DATA"));

// Init messagebird
const messagebird = initMB(MESSAGE_BIRD_API_KEY);

// Send message to the whatsapp number
async function sendMessage() {
  const params = {
    to: PHONE_NUMBER,
    from: WHATSAPP_CHANNEL_ID,
    type: "text",
    content: {
      text: TEXT,
      disableUrlPreview: false,
    },
    reportUrl: REPORT_URL,
  };

  await messagebird.conversations.send(params, function (err, response) {
    if (err) {
      throw new Error(err);
    }
    console.log(response);
    return response;
  });
}

await sendMessage();
