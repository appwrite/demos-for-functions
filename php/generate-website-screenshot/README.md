# 📧  Generate Website Screenshot
A sample PHP Cloud Function for taking screenshot of a website.(Use CloudConvert CaptureWebsite API)

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_API_KEY** - Your Appwrite API key with `files.read` and `files.write` permissions
* **CLOUDCONVERT_API_KEY** - API Key for Cloudconvert
* **APPWRITE_SCREENSHOT_URL** - Full path URL to screenshot (For example: https://www.mywebsite.com)

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/php/generate-website-screenshot
$ composer install
$ tar -zcvf code.tar.gz .
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `php index.php`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger
After the Building and Packaging step, Press the Execute Now button.