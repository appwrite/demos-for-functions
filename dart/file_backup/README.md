# 🗂 File Backup using the Dropbox API
A sample Dart Cloud Function that leverages Dropbox to create backups of important files uploaded to Appwrite.

## 📝 Environment Variables
Add the following environment variables in your Cloud Function settings.

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.read`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **DROPBOX_KEY** - OAuth token from [Dropbox](https://blogs.dropbox.com/developers/2014/05/generate-an-access-token-for-your-own-account) 

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dart/file_backup

$ PUB_CACHE=.appwrite/ dart pub get
```

* Ensure that your folder structure looks like this 
```
.
├── .appwrite/
├── main.dart
├── pubspec.lock
└── pubspec.yaml
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz file_backup
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
dart main.dart
```

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `storage.files.create` event.

If your function errors out with code 124, increase the timeout under the Settings Tab.