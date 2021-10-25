# 📷 Generate thumbnail using Cloud Convert API
A Kotlin Cloud Function that generates thumbnail using [CloudConvert API](https://cloudconvert.com/login#thumbnail-tasks)

## 📝 Environment Variables
When running function, pass appwrite Storage ID of the file you want thumbnail for as ***function data***.

- **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
- **APPWRITE_API_KEY** - Your Appwrite API key with `files.read` and `files.write` permissions
- **CLOUDCONVERT_API_KEY** - Your Cloud Convert API Key with `tasks.read` and `tasks.write` scopes
- **CLOUDCONVERT_SANDBOX** - Are you using Sandbox? Set to `false` if you don't know what it means
- **CLOUDCONVERT_WEBHOOK_SIGNING_SECRET** - CloudConvert Webhook secret
- **APPWRITE_FUNCTION_DATA** - ID of the file you want thumbnail for, in appwrite storage

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, or any other IDE that has support for Kotlin projects.

* Build a jar for the project. Follow the instructions [here](https://hardiksachan.hashnode.dev/build-a-jar-with-gradle)

* Create a tarfile

```bash
$ cd demos-for-functions/kotlin/generate-cloud-convert-thumbnail/build/libs/
$ tar -zcvf code.tar.gz .\generate-cloud-convert-thumbnail-1.0-SNAPSHOT.jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar generate-cloud-convert-thumbnail-1.0-SNAPSHOT.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'

## 📝 A note on jar size
This build size exceeds 10.0 MB default limit, to change it, [read this](https://appwrite.io/docs/environment-variables).
You can update the value of `_APP_STORAGE_LIMIT` from `.env` file and run `docker-compose up -d`.

## 🎯 Trigger
Can be triggered from manually from the Appwrite Console.
