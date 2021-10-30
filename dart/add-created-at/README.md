# ⏳ Add `createdAt` timestamp on document creation 
A sample Dart Cloud Function which triggers on `database.documents.create` event and fill `createdAt` attribute with the current time in milliseconds since epoch, if this rule for createdAt is configured on a specific collection.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_PROJECT_ID** - Your AppWrite ProjectID
* **API_KEY** - Your Appwrite API key with `documents.read` and `documents.write` permissions

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dart/add-created-at

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
$ tar -zcvf code.tar.gz add-created-at
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dart main.dart`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## ⏰ Note

The `createdAt` field is in milliseconds since epoch