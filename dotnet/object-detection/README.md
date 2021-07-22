# 📷 Object Detection using Cloudmersive Vision API
A sample .Net Cloud Function for object detection on an image file uploaded by the user. 

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_PROJECT_ID** - Your Project ID
* **APPWRITE_API_KEY** - Your Appwrite API key with `files.read` and `files.write` permissions
* **CLOUDMERSIVE_API_KEY** - API key acquired from https://cloudmersive.com. There is free trie.

## 📝 Default Environment Variables
No need to add these variables, these are supplied by Appwrite itself. 

* **APPWRITE_FUNCTION_EVENT_DATA** - Your function event payload. This value is available only when your function trigger is 'event'. This variable value contains a string in JSON format with your specific event data.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/object-detection

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like this 
```
  StorageCleaner -> ......\demos-for-functions\dotnet\storage-cleaner\bin\Debug\net5.0\linux-x64\StorageCleaner.dll
  StorageCleaner -> ......\demos-for-functions\dotnet\storage-cleaner\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dotnet ObjectDetection.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'
* Navigate to the Settings Tab of your Cloud Function > Events > select storage.files.create
* (optional) set Timeout to some 30s as function may timeout before  fisnishing
