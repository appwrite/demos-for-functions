# 📧 Converting image file from X format to JPG format using CloudConvert API

A sample Node.js Cloud Function for converting a given image in Appwrite storage to .jpg format.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_API_ENDPOINT** - Appwrite API endpoint
- **APPWRITE_PROJECT_ID** - Appwrite project's ID
- **APPWRITE_API_KEY** - Appwrite project's API key
- **CLOUD_CONVERT_API_KEY** - CloudConvert's API created through Authorization > API Keys on Dashboard

You need to supply the id of the file (from your storage) as parameter to the function in this format- { "id": YOUR_FILE_ID }

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/convert-cloud-convert-file

$ npm install
```

- Ensure that your folder structure looks like this

```
.
├── index.js
├── .env
├── node_modules/
├── package-lock.json
└── package.json
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz convert-cloud-convert-file
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "node index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Overview Tab, click Execute Now and supply id of image to convert in JSON format.
