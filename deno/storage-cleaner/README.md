# 🚮 Clean up files in your storage older than XX days

A sample Deno Cloud Function for deleting files that are older than XX days on a schedule.

## ☁️ Make a New Cloud Function
Navigate to 'Functions' and 'Add Function.'
Use 'Deno 1.5' environment.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
- **APPWRITE_API_KEY** - Your Appwrite API key with `files.read` and `files.write` permissions
- **DAYS_TO_EXPIRE** - Days for files to expire

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/storage-cleaner
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz storage-cleaner
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "deno run --allow-net --allow-env index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## ⏰ Schedule
Head over to your function in the Appwrite console and under the Settings Tab,, enter a reasonable schedule time (cron syntax).

For example:

- `*/30 * * * *` every 30 minutes
- `0 * * * *` every hour
- `0 0 * * *` every day