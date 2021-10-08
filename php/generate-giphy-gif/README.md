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

## 👀 Preview
![gif1](https://user-images.githubusercontent.com/13732765/136447730-6aef67f5-367d-443e-96d2-e7de84cac5f0.png)
![gif2](https://user-images.githubusercontent.com/13732765/136447737-70da6cc9-7779-4486-9eec-d9324c211f0e.png)
![gif3](https://user-images.githubusercontent.com/13732765/136447744-cff36c5d-6844-44c7-9f47-2f62f721a121.png)