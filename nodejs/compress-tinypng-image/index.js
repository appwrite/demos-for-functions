// Perform all your imports
const sdk = require('node-appwrite');
const tinify = require('tinify');
const {Readable}=require('stream');

// Initialise the client SDK
let client = new sdk.Client();
client
    .setEndpoint("http://192.168.1.13/v1"/*process.env.APPWRITE_ENDPOINT*/) // Your API Endpoint
    .setProject(process.env.APPWRITE_FUNCTION_PROJECT_ID) // Your project ID available by default
    .setKey(process.env.APPWRITE_API_KEY) // Your secret API key
   ;

// Initialise the storage SDK
let storage = new sdk.Storage(client);

const api_key = process.env.TINYPNG_API_KEY; //Your TinyPNG API key
const url = process.env.APPWRITE_FUNCTION_DATA; //The link you need to provide (Custom Data)
let output_name= process.env.IMAGE_OUTPUT_NAME; //The output name of the image. tinified_image.png by default

tinify.key=api_key;

(async () => {
  if(!url){
    console.log('Please input a link.');
    return 0;
  }
  if(!output_name){
    output_name='tinified_image.png'
  }
  let source = await tinify.fromUrl(url); //Post the picture to TinyPNG API

  //Adding binary source in a buffer then in a readable stream
  let buffer = await source.toBuffer();
  let stream = Readable.from(buffer);

  //Force the name of the file 
  stream.name = output_name;

  //Put the file inside the appwrite storage
  let store = storage.createFile(stream);
  store.then((res)=>{
      console.log("Result:")
      console.log(res); // Success
  }).catch((err)=>
      console.error("Error:\n"+err));
})();