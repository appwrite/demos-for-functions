import * as sdk from "https://deno.land/x/appwrite/mod.ts";

// Init SDK
let client = new sdk.Client();
client
    .setEndpoint('http://localhost/v1')
    .setProject('5fca866c65afc') // Your project ID
    .setKey('8d84bc37d4c59bb3b3....f1ab664e94e6df53') // Your secret API key
;

let storage = new sdk.Storage(client);
let promise = storage.getFile('[FILE_ID]');

promise.then(function (response) {
    console.log(response);
}, function (error) {
    console.log(error);
});