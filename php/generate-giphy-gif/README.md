# 🖼️  Grab GIF from the Giphy API
This is a demo PHP function to show how to get a GIF from the Giphy API through a search query.

## 📝 Environment Variables
Go to the Settings tab of your Cloud Function. Add the following environment variables:

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

## 👀 Preview
Set Giphy API key
![gif1](https://user-images.githubusercontent.com/13732765/138771400-0558f630-4bfd-470c-97dc-fdefef0c1e89.png)

Trigger function manually, 'custom data' is your search query
![gif2](https://user-images.githubusercontent.com/13732765/138771532-2e6b9076-e935-45c4-9044-b7cf460b1e95.png)

Success logs
![gif3](https://user-images.githubusercontent.com/13732765/136447737-70da6cc9-7779-4486-9eec-d9324c211f0e.png)

Output example
![gif4](https://user-images.githubusercontent.com/13732765/136447744-cff36c5d-6844-44c7-9f47-2f62f721a121.png)