# 📧 Get documents from a collection from Appwrite Database and export them into a csv to Appwrite storage

## 📝 Environment Variables
Make sure to set the following environment variables.

* **APPWRITE_ENDPOINT** - AppWrite Domain 
* **APPWRITE_FUNCTION_PROJECT_ID** - AppWrite Project ID
* **APPWRITE_API_KEY** - AppWrite API key

## 🚀 Building and Packaging

To use this example, follow these steps.

```bash
$ cd demos-for-functions/php/backup-collections

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
php index.php collectionId ...
```
