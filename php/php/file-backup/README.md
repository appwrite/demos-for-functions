# 📧 Get Files from Appwrite storage and upload them to Dropbox
A sample PHP Cloud Function for uploading files to Dropbox

## 📝 Environment Variables
Make sure to set the following environment variables.

* **APPWRITE_ENDPOINT** - AppWrite Domain 
* **APPWRITE_FUNCTION_PROJECT_ID** - AppWrite Project ID
* **APPWRITE_API_KEY** - AppWrite API key
* **DROPBOX_ACCESS_TOKEN** - Dropbox Access Token

## 🚀 Building and Packaging

To use this example, follow these steps.

```bash
$ cd demos-for-functions/php/file-backup

$ composer update
```

* Ensure that your folder structure looks like this 
```
.
├── index.php
├── vendor/
├── composer.json
└── composer.lock
└── README.md
```

## 🎯 Trigger

```bash 
php index.php fileId1 fileId2 ...
```
