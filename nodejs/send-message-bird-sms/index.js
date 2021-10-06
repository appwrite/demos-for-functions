const MESSAGE_BIRD_LIVE_API_KEY = process.env.MESSAGE_BIRD_LIVE_API_KEY;
const ORIGINATOR_PHONE_NUMBER = process.env.ORIGINATOR_PHONE_NUMBER;

const messagebird = require('messagebird')(MESSAGE_BIRD_LIVE_API_KEY);

const payload = JSON.parse(process.env.APPWRITE_FUNCTION_DATA);
const phoneNumber = payload['phoneNumber'];
const text = payload['text'];

messagebird.messages.create({
  originator : ORIGINATOR_PHONE_NUMBER,
  recipients : [ phoneNumber ],
  body : text
}, (err, response) => {
  if (err) {
    console.log("ERROR:");
    console.log(err);
  } else {
    console.log("SUCCESS:");
    console.log(response);
  }
});
