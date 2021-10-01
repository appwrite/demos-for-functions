const accountSid = process.env.TWILIO_ACCOUNT_SID;
const authToken = process.env.TWILIO_AUTH_TOKEN;

const senderPhone = process.env.TWILIO_NUMBER;

const client = require('twilio')(accountSid, authToken);

// Fetching the receiver's number and message from Appwrite's environment variable
const payload = JSON.parse(process.env.APPWRITE_FUNCTION_DATA);
const receiver = payload['receiver'];
const message = payload['message'];

if (!receiver) throw new Error("Receiver's phone number is required!");
if (!message) throw new Error("Message content is required!");

client.messages
    .create({
        from: senderPhone,
        to: receiver,
        body: message
    })
    .then((response) => {
        console.log(response)
    })
    .catch((err) => {
        console.error(err);
    });
