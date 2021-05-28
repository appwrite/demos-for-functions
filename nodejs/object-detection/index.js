// AppWrite SDK
const sdk = require('node-appwrite');

// CloudMersive client
const CloudmersiveImageApiClient = require('cloudmersive-image-api-client');

// Get data from environment variables
const CLOUDMERSIVE_API_KEY = process.env.CLOUDMERSIVE_API_KEY;
const APPWRITE_ENDPOINT = process.env.APPWRITE_ENDPOINT;
const APPWRITE_PROJECT_ID = process.env.APPWRITE_PROJECT_ID;
const APPWRITE_API_KEY = process.env.APPWRITE_API_KEY;

// Contains the data related to the storage.files.create event
const APPWRITE_FUNCTION_EVENT_DATA = process.env.APPWRITE_FUNCTION_EVENT_DATA;
const { $id: fileID } = JSON.parse(APPWRITE_FUNCTION_EVENT_DATA);

// Configure the AppWrite SDK
const appwriteClient = new sdk.Client();
appwriteClient
  .setEndpoint(APPWRITE_ENDPOINT)
  .setProject(APPWRITE_PROJECT_ID)
  .setKey(APPWRITE_API_KEY);

const storage = new sdk.Storage(appwriteClient);

// Configure the CloudMersive client
const cloudmersiveDefaultClient = CloudmersiveImageApiClient.ApiClient.instance;
cloudmersiveDefaultClient.authentications.Apikey.apiKey = CLOUDMERSIVE_API_KEY;

const cloudmersiveRecognizeApi = new CloudmersiveImageApiClient.RecognizeApi();

// Get the image file
storage
  .getFilePreview(fileID)
  .then((file) => {
    // Recognize objects in the retrieved image
    cloudmersiveRecognizeApi.recognizeDetectObjects(file, (error, res) => {
      if (error) {
        console.error('Exception when calling CloudmersiveRecognizeApi', e);
      } else {
        console.log(res);
      }
    });
  })
  .catch((e) => {
    console.error('An error occured during fetch of image preview', e);
  });
