// Perform all your imports
const sdk = require('node-appwrite');

// Initialise the client SDK
let client = new sdk.Client();
client
    .setEndpoint(process.env.APPWRITE_ENDPOINT) // Your API Endpoint
    .setProject(process.env.APPWRITE_PROJECT_ID) // Your project ID
    .setKey(process.env.APPWRITE_API_KEY) // Your secret API key
    ;

// Initialise the storage SDK
let storage = new sdk.Storage(client);

(async () => {
    let listFile = await storage.listFiles('', 100, 0, 'DESC');
    let days = parseFloat(process.env.DAYS_TO_EXPIRE);
    let timestamp = new Date();
    timestamp = timestamp.setDate(timestamp.getDate() - days);
    let deletedFiles = 0;

    for (const file of listFile.files) {
        if (file.dateCreated * 1000 < timestamp) {
            await storage.deleteFile(file.$id);
            console.log(`Deleted ${file.$id}`);
            deletedFiles++;
        }
    }
    console.log(`Total files deleted: ${deletedFiles}`);
})();

