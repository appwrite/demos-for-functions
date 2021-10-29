# 📧 Converting image file from X format to JPG/PNG format using CloudConvert API
A sample PHP Cloud Function for converting a given image in Appwrite storage to .jpg/.png format.

**Make sure your Appwrite instance file size limit is set according to your needs. The default is 10 MB.**
## 📝 Environment Variables
Go to the Settings tab of your Cloud Function. Add the following environment variables.
- **APPWRITE_API_ENDPOINT** - Appwrite API endpoint
- **APPWRITE_API_KEY** - Appwrite project's API key
- **CLOUDCONVERT_API_KEY** - CloudConvert's API key created through Authorization > API Keys on Dashboard

You need to supply the ID of the file (from your app storage) and output format ("png" or "jpg") as parameters to the function in this format- { "file_id": "YOUR_FILE_ID", "output_format": "YOUR_FORMAT" }
## 🚀 Building and Packaging
To package this example as a cloud function, create a .tar file of the code and deploy it on the Appwrite dashboard.

#### Create .tar file
```bash
$ cd php/convert-cloud-convert-file
$ tar -zcvf code.tar.gz . 
```
#### Deploy on Appwrite dashboard
* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `php -f index.php`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'
#### Deploy with Appwrite CLI
If you want to use the Appwrite CLI no .tar packaging is needed and you can use the createTag command. Use 
```bash
appwrite functions createTag help
```
to learn more about the command. You have to install the Appwrite CLI first.


## 🎯 Schedule
Head over to your function in the Appwrite console and under the Settings Tab, enter a reasonable schedule time (cron syntax).

For example:

- `*/30 * * * *` every 30 minutes
- `0 * * * *` every hour
- `0 0 * * *` every day