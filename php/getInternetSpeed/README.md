## 📧 Get Server's Download/Upload speed
A sample PHP Cloud Function to check the download and upload speed of the server.

## 📝 Environment Variables
No environment variables needed.
  
🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/php/getInternetSpeed
$ composer install
$ tar -zcvf code.tar.gz .
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `php index.php`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'
* **Important:** Increase the timeout for the function to at least 120 sec or more (in Function setting > Timeout field).
* Depending on the location and the server's speed, you will need to adjust the timeout accordingly.

## 🎯 Trigger
After the Building and Packaging step, follow the following steps:

1. Press the Execute Now button.
2. Press Again Execute Now Button.
3. Wait for the function to finish (it might take 30 seconds to complete)
4. Check the Logs for the result
Example of output: `{"DownloadSpeed":"75.37Mbps","UploadSpeed":"35.28Mbps"}`