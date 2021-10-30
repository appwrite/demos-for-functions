const sdk = require("node-appwrite");
require("dotenv").config();

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
    return console.error(err);
  }
  console.log(response);
});
