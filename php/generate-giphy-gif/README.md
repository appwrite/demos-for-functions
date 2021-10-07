# 🖼️  Grab GIF from the Giphy API
This is a demo PHP function to show how to get a GIF from the Giphy API through a search query.

## 📝 Environment Variables
Go to the Settings tab of your Cloud Function. Add the following environment variables:

* **GIPHY_API_KEY** - Get your API key here: https://developers.giphy.com/docs/api#quick-start-guide
* **GIPHY_QUERY** - Search query

You can also send the Giphy search query as custom data string when calling the function.

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
Go to the Settings tab of your Cloud Function. Select the relevant event to fire this function or schedule it. You can also execute this function manually from the Overview tab.