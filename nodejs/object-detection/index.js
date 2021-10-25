// Perform all your imports
const sdk = require('node-appwrite');
const CloudmersiveClient = require('cloudmersive-image-api-client');

// Initialise the client SDK
const client = new sdk.Client();
client.setEndpoint(process.env.APPWRITE_ENDPOINT)
  .setProject(process.env.APPWRITE_FUNCTION_PROJECT_ID)
  .setKey(process.env.APPWRITE_API_KEY);

// Initialise the storage SDK
const storage = new sdk.Storage(client);

// Read the file ID 
const payload = JSON.parse(process.env.APPWRITE_FUNCTION_EVENT_DATA);
const fileId = payload.$id;

// Initialise the Cloudmersive Recognition API  
const cloudmersiveClient = CloudmersiveClient.ApiClient.instance;
const ApiKey = cloudmersiveClient.authentications['Apikey'];
ApiKey.apiKey = process.env.CLOUDMERSIVE_API_KEY;

const api = new CloudmersiveClient.RecognizeApi();

const recognizeAsPromise = (buffer) => {
  return new Promise((res, rej) => {
    const callback = (err, data) => {
      if (err) {
        return rej(err);
      }

      res(data);
    };

    api.recognizeDetectObjects(buffer, callback);
  });
};

(async () => {
  try {
    const buffer = await storage.getFilePreview(fileId);
    const result = await recognizeAsPromise(buffer);
    console.log(JSON.stringify(result, null, 4));
  } catch (e) {
    console.error('Exception when trying to recognize objects from file:');
    console.error(e);
  }
})();
