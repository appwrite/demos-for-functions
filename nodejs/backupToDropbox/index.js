// Perform all your imports
const sdk = require('node-appwrite');
require('dotenv').config();

// Initialise the client SDK
let client = new sdk.Client();
client
    .setEndpoint('https://[HOSTNAME_OR_IP]/v1') // Your API Endpoint
    .setProject(process.env.APPWRITE_FUNCTION_PROJECT_ID) // Your project ID available by default
    .setKey('919c2d18fb5d4...a2ae413da83346ad2') // Your secret API key
;

// Initialise the storage SDK
const storage = new sdk.Storage(client);
const collection = new sdk.Database(client);

collection.createDocument('6162a6ab09478', 'hello');