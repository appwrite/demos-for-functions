// Perform all your imports
const sdk = require('node-appwrite');

// Initialise the client SDK
let client = new sdk.Client();
client
    .setEndpoint('https://[HOSTNAME_OR_IP]/v1') // Your API Endpoint
    .setProject(process.env.APPWRITE_FUNCTION_PROJECT_ID) // Your project ID available by default
    .setKey('919c2d18fb5d4...a2ae413da83346ad2') // Your secret API key
;

// Initialise the storage SDK
let storage = new sdk.Storage(client);
let promise = storage.listFiles();

promise.then(function (response) {
    console.log(response);
}, function (error) {
    console.log(error);
});