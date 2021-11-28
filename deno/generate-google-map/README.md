# 🚮 Generate Google Map Image
A sample Deno Cloud Function to Generate Google Map Image

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_ENDPOINT** - Your Appwrite Project Endpoint
- **APPWRITE_API_KEY** - Your Appwrite API key with `files.write` permission
- **APPWRITE_FUNCTION_PROJECT_ID** - Your Appwrite Project Id 
- **GOOGLEMAPS_API_KEY** - Your Google Maps API key

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/generate-google-map
$ tar -zcvf code.tar.gz .
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `deno run --allow-env --allow-net mod.js`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## Trigger

Head over to your function in the Appwrite console and just press **Execute Now**. 
You must pass json data in the Custom Data form including `latitude` and `longitude` (the other fields are optional) e.g.:

```json
{
    "latitude": "40.714728",
    "longitude": "-73.998672",
    "zoom": "15",
    "width": "600",
    "height": "300"
}
```

Example response:
```
{
  "$id": "617a006d10b0d",
  "$permissions": { read: [], write: [] },
  name: "map-1635385453016.png",
  dateCreated: 1635385453,
  signature: "718adf8a00772d302e6a8a5385d5c577",
  mimeType: "image/png",
  sizeOriginal: 56363
}
```