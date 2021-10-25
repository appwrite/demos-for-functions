# Object Detection
A Dart Cloud Function for detecting objects in an uploaded image.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_API_KEY** - Your Appwrite API key with `files.read` and `files.write` permissions
* **CLOUDMERSIVE_API_KEY** - Your Cloudmersive Api Key

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dart/object-detection

$ export PUB_CACHE=.appwrite/
$ dart pub get

```
* Ensure that your folder structure looks like this 
```
.
├── main.dart
├── .appwrite/
├── pubspec.lock
└── pubspec.yaml
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz object-detection
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function i.e. `dart main.dart` as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

##🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, under the Events section select `storage.files.create`. This would enable this function to run everytime a new file is created/uploaded in Storage.

Increase the timeout if the function fails with error code: 124.
