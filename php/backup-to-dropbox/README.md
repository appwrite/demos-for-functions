# 📧  Backup to Dropbox
A sample PHP Cloud Function for exporting all data from all collections, generate an in-memory CSV file and save it to Dropbox.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_API_KEY** - Your Appwrite API key with `collections.read`, `documents.read` permissions
* **DROPBOX_ACCESS_TOKEN** - API Key for DropBox with `files.content.write` permissions

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/php/backup-to-dropbox
$ composer install
$ tar -zcvf code.tar.gz .
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `php index.php`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger
After the Building and Packaging step, follow the following steps:
1. Press the Execute Now button
2. Press Again Execute Now Button