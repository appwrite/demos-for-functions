import * as sdk from "https://deno.land/x/appwrite@0.4.1/mod.ts";

// Init SDK
let client = new sdk.Client();
client
  .setEndpoint(Deno.env.get("APPWRITE_ENDPOINT"))
  .setProject(Deno.env.get("APPWRITE_FUNCTION_PROJECT_ID")) // Your project ID available by default
  .setKey(Deno.env.get("APPWRITE_API_KEY")); // Your secret API key

let storage = new sdk.Storage(client);

const googlemapsApiKey = Deno.env.get("GOOGLEMAPS_API_KEY");

let settings = {
  zoom: 13,
  width: "600",
  height: "300",
  ...JSON.parse(Deno.env.get("APPWRITE_FUNCTION_DATA"))
}

if (!settings.latitude || !settings.longitude) {
  throw new Error("Missing latitude and/or longitude");
}

const imageUrl = `https://maps.googleapis.com/maps/api/staticmap?center=${settings.latitude},${settings.longitude}&zoom=${settings.zoom}&size=${settings.width}x${settings.height}&key=${googlemapsApiKey}`;

const blob = await fetch(imageUrl).then((file) => file.blob());

// Blob to File
const mapImage = new File([blob], `map-${Date.now()}.png`);

let promise = storage.createFile(mapImage);

promise.then(function (response) {
    console.log(response);
}, function (error) {
    console.log(error);
});

