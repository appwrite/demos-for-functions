# 📦 Backups using Appwrite Storage
A sample Deno Cloud Function for making a backup of the DataBase

## ☁️ Make a New Cloud Function
Navigate to 'Functions' and 'Add Function.'
Use 'Deno 1.5' environment.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_ENDPOINT** - Appwrite Endpoint to use 
* **APPWRITE_PROJECT_ID** - Project ID of the Appwrite Project
* **APPWRITE_API_KEY** - API Key to interact with Appwrite

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/file-backup
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz file-backup
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "deno --unstable run --allow-net --allow-env --allow-read --allow-write index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger
You can trigger the function by clicking on "Execute now" in the Appwrite console or using `createExecution` function from one of our Client-side or Server-side SDKs.
