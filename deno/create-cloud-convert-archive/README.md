# 📧 Create archive of files using CloudConvert API

A sample Deno Cloud Function for create archive of a group of files in Appwrite storage.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_API_ENDPOINT** - Appwrite API endpoint
- **APPWRITE_PROJECT_ID** - Appwrite project's ID
- **APPWRITE_API_KEY** - Appwrite project's API key
- **CLOUD_CONVERT_API_KEY** - CloudConvert's API created through Authorization > API Keys on Dashboard

You need to supply the IDs of the files (from your storage) as parameter to the function in this format- { "ids": [FILE_ID_1, FILE_ID_2, ...] }

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

- Create a tarfile

```bash
$ cd demos-for-functions/deno/create-cloud-convert-archive
$ tar -zcvf code.tar.gz .
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "deno run --allow-net --allow-read --allow-env index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Overview Tab, click Execute Now and supply ids of the files in JSON format.
