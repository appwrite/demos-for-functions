# 📧 generateCloudConvertThumbnail

Cloud funtion to generate thumbnail.

## 📝 Environment Variables

<!-- Tell the users of your Cloud function, what Environment Variables your function uses. Use the following format -->

- **APPWRITE_API_ENDPOINT** - Cloud Function API Endpoint
- **APPWRITE_API_KEY** - API key created in the AppWrite console
- **APPWRITE_FUNCTION_DATA** - File ID (Pass the file while executing the function in console)
- **THUMBNAIL_WIDTH** - Width of the thumbnail e.g, 300
- **THUMBNAIL_HEIGHT** - Height of the thumbnail e.g., 400
- **CLOUD_CONVERT_API_KEY** - Cloud Convert API Key

Example:  
APPWRITE_FUNCTION_DATA=61603f3be53f1

## 🚀 Building and Packaging

To package this as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/generateCloudConvertThumbnail

```

- Ensure that your folder structure looks like this

```
.
├── index.js
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz generateCloudConvertThumbnail
```

- Navigate to Overview Tab of your Cloud Function
- Deploy Tag
- Input the command that will run your function (in this case "deno run --allow-net --allow-env index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Overview Tab, click Execute Now and supply id of file in string format to generate thumbnail .
