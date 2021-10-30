# 📧  Optimize image in CloudConvert
A sample PHP Cloud Function for optimizing and compressing images.(Use CloudConvert Optimize API)

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_API_KEY** - Your Appwrite API key with `files.read` and `files.write` permissions
* **CLOUDCONVERT_API_KEY** - API Key for Cloudconvert

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/php/optimize-cloud-convert-file
$ composer install
$ tar -zcvf code.tar.gz .
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `php index.php`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger
After the Building and Packaging step, 
1. Press the Execute Now button
2. Enter in the Custom Data field the file ID you want to optimize (example: 616a9ea27dc21)
3. Press the Execute Now button again
