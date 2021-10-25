# 📧 generateCloudConvertThumbnail

Cloud funtion to generate thumbnail.

## 📝 Environment Variables

<!-- Tell the users of your Cloud function, what Environment Variables your function uses. Use the following format -->

- **APPWRITE_API_ENDPOINT** - Cloud Function API Endpoint
- **APPWRITE_API_KEY** - API key created in the AppWrite console
- **APPWRITE_FUNCTION_DATA** - File ID (Pass the file while executing the function in console)
- **THUMBNAIL_WIDTH** - Width of the thumbnail e.g, 300
- **THUMBNAIL_HEIGHT** - Height of the thumbnail e.g., 400
- **APPWRITE_CLOUD_CONVERT_API_KEY** - Cloud Convert API Key




Example:  
APPWRITE_FUNCTION_EVENT_DATA="73df1e16-fd8b-47a1-a156-f197babde91a"

## 🚀 Building and Packaging

To package this as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/generateCloudConvertThumbnail

$ npm install
```

- Ensure that your folder structure looks like this

```
.
├── index.js
├── node_modules/
├── package-lock.json
└── package.json
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz generateCloudConvertThumbnail
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "node index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

To trigger this function, you can either click `Execute now` button in Appwrite console or use one of our Server-side/Client-side SDKs (`createExecution`)