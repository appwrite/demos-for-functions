# 📧  Generate GDPR Export

This function should find all documents in all collections where the ID of the user who executed the function is mentioned and generate a CSV file that will be saved to Appwrite Storage.

This function output is appwrite file object [File Object](https://appwrite.io/docs/models/file).

## 📝 Environment Variables

- **APPWRITE_ENDPOINT** - Your Appwrite Project Endpoint ( can be found at `settings` tab on your Appwrite console)
- **APPWRITE_API_KEY** - Your Appwrite Project API Keys ( can be found at `API Keys` tab on your Appwrite console, it needs `collections.read`, `documents.read` and `files.write` permissions)
- **APPWRITE_FUNCTION_PROJECT_ID** - Your Appwrite Project Id ( can be found at `settings` tab on your Appwrite console)

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/generate-gdpr-export
```

* Ensure that your folder structure looks like this 
```
.
├── mod.ts
├── README.md
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz generate-gdpr-export
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "deno run --allow-all mod.ts") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## Usage

This function executed in client, and need userId data.

* Example Using Web SDK:
```
const sdk = new Appwrite();

sdk
    .setEndpoint('https://[HOSTNAME_OR_IP]/v1') // Your API Endpoint
    .setProject('5df5acd0d48c2') // Your project ID
;

let promise = sdk.functions.createExecution(
  '[FUNCTION_ID]', // Your Function ID
);

promise.then(function (response) {
    console.log(response); // Success
}, function (error) {
    console.log(error); // Failure
});

```
