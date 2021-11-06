//Perform all your imports
const sdk = require('node-appwrite');
const adm = require("firebase-admin");

//place your firebase exported serviceAccountKey.json in same folder where your index.js is
var serviceAccount = require("/usr/local/src/serviceAccountKey.json");

adm.initializeApp({
    credential: adm.credential.cert(serviceAccount),
    databaseURL: "<place_your_firebase_url_here>"
});

// Initialise the client SDK
let client = new sdk.Client();
client
    .setEndpoint(process.env.APPWRITE_ENDPOINT)
    .setProject(process.env.APPWRITE_PROJECT_ID)
    .setKey(process.env.APPWRITE_API_KEY);

const payload = JSON.parse(process.env.APPWRITE_FUNCTION_DATA);
const title = payload['title'];
const body = payload['body'];
const sender = payload['sender'];
const message = payload['message'];
const token = "<place_user_token_here>";

var pushPayload = {
    "notification": {
        "title": title,
        "body": body,
        "sound": "default"
    },
    "data": {
        "sendername": sender,
        "message": message
    }
};

(async () => {
    await adm.messaging().sendToDevice(token, pushPayload).then((response) => {
        console.log('it worked');
    }).catch((err) => {
        console.log(err);
    });
}
)();
