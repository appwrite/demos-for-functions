# 🖼️  Grab GIF from the Giphy API
This is a demo PHP function to show how to get a GIF from the Giphy API through a search query.

## 📝 Environment Variables
Go to the Settings tab of your Cloud Function. Add the following environment variable:

* **GIPHY_API_KEY** - Get your API key here: https://developers.giphy.com/docs/api#quick-start-guide

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps:
```bash
$ cd demos-for-functions/php/generate-giphy-gif

$ tar -zcvf code.tar.gz .
```
* Navigate to the Overview tab of your Cloud Function > Deploy Tag
* Navigate to the Manual tab
* Input the command: `php index.php`
* Upload your tarfile 
* Click 'Create', and 'Activate' after, to activate the tag

## 🎯 Trigger
You can trigger this function manually from the Overview tab tab of your Cloud Function or by using `executeFunction` from SDK.
