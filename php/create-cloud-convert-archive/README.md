# 📧 Archive files using CloudConverter's `createCloudConvertArchive` function
A sample PHP Cloud Function for archiving files from AppWrite storage.

## 📝 Environment Variables
Make sure to set the following environment variables.

* **APPWRITE_ENDPOINT** - AppWrite Domain 
* **APPWRITE_FUNCTION_PROJECT_ID** - AppWrite Project ID
* **APPWRITE_API_KEY** - AppWrite API key
* **CC_API_KEY** - CloudConverter API key

## 🚀 Building and Packaging

To use this example, follow these steps.

```bash
$ cd demos-for-functions/php/create-cloud-convert-archive

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
