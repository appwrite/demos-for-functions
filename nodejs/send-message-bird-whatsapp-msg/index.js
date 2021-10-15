const sdk = require("node-appwrite");
require("dotenv").config();

const client = new sdk.Client();

client
  .setEndpoint(process.env.APPWRITE_API_ENDPOINT) // Your API Endpoint
  .setProject(process.env.APPWRITE_PROJECT_ID) // Your project ID
  .setKey(process.env.APPWRITE_API_KEY); // Your secret API key

const appwriteParameters = JSON.parse(process.env.APPWRITE_FUNCTION_DATA);

const messagebird = require("messagebird")(process.env.MESSAGEBIRD_API_KEY);
const params = {
  to: appwriteParameters.phone,
  from: process.env.WHATSAPP_CHANNEL_ID,
  type: "text",
  content: {
    text: appwriteParameters.text,
    disableUrlPreview: false,
  },
};

messagebird.conversations.send(params, function (err, response) {
  if (err) {
    return console.log(err);
  }
  console.log(response);
});
