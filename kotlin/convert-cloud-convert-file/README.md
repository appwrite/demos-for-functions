# 📧 Converting image file from X format to JPG/PNG format using CloudConvert API

A sample Kotlin Cloud Function for converting a given image in Appwrite storage to .jpg/.png format.

**NB! If your Appwrite instance use the default 10 MB file size limit, you need to bump it to at least 15 MB.**

## 📝 Environment Variables

Go to the Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_API_ENDPOINT** - Appwrite API endpoint
- **APPWRITE_API_KEY** - Appwrite project's API key
- **CLOUDCONVERT_API_KEY** - CloudConvert's API key created through Authorization > API Keys on Dashboard
- **CLOUDCONVERT_WEBHOOK_SIGNING_SECRET** - Random secret for CloudConvert webhook (required by SDK)

You need to supply the ID of the file (from your app storage) and output format ("png" or "jpg") as parameters to the function in this format- { "file": "YOUR_FILE_ID", "output_format": "YOUR_FORMAT" }

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, Eclipse or any other IDE that has support for Kotlin projects.

* Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

* Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/kotlin/convert-cloud-convert-file/out/artifacts/convert_cloud_convert_file_jar/convert-cloud-convert-file.jar`

```bash
$ cd kotlin/convert-cloud-convert-file/out/artifacts
$ tar -zcvf code.tar.gz convert_cloud_convert_file_jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar convert-cloud-convert-file.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'

## ⏰ Schedule

Head over to your function in the Appwrite console and under the Settings Tab, enter a reasonable schedule time (cron syntax).

For example:

- `*/30 * * * *` every 30 minutes
- `0 * * * *` every hour
- `0 0 * * *` every day
