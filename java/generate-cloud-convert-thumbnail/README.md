# 🖼️ Generate thumbnail using Cloud Convert API
A sample Java Cloud Function for generating thumbnail of an Appwrite Storage file using [CloudConvert API](https://cloudconvert.com/login#thumbnail-tasks) and saving the thumbnail back to Appwrite Storage.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_ENDPOINT** - Appwrite Endpoint.
* **APPWRITE_API_KEY** - Appwrite API key with `files.read` and `files.write` permissions
* **APPWRITE_FUNCTION_DATA** - the Appwrite Storage file ID of the input file
* **CLOUDCONVERT_API_KEY** - API Key for Cloud Convert API with `tasks.read` and `tasks.write` permissions
* **CLOUDCONVERT_WEBHOOK_SIGNING_SECRET** - Cloud Convert Webhook secret
* **CLOUDCONVERT_SANDBOX** - `true` for Cloud Convert Sandbox and `false` for Cloud Convert Live 

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, Eclipse or any other IDE that has support for Java projects.


* Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

* Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/java/generate-cloud-convert-thumbnail/out/artifacts/generate_cloud_convert_thumbnail_jar/generate-cloud-convert-thumbnail.jar`

```bash
$ cd demos-for-functions/java/generate-cloud-convert-thumbnail/out/artifacts/
$ tar -zcvf code.tar.gz generate_cloud_convert_thumbnail_jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar generate-cloud-convert-thumbnail.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'

## 🎯 Trigger

Trigger the cloud function using the SDK, HTTP API or the Appwrite Console.

- Sample Input: the Appwrite Storage file ID of the input file

```
617844e8ed11d
```

Sample response: the Appwrite Storage file ID of the output file (Cloud Convert thumbnail)

```
617848df25897
```