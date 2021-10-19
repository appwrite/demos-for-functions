# 📧  Backup to Dropbox
This function should export all data from all collections, generate an in-memory CSV file and save it to Dropbox. This function output is info about the backup.

## 📝 Environment Variables

- **APPWRITE_ENDPOINT** - Your Appwrite Project Endpoint ( can be found at `settings` tab on your Appwrite console)
- **APPWRITE_API_KEY** - Your Appwrite Project API Keys ( can be found at `API Keys` tab on your Appwrite console, it needs `collections.read`, `documents.read` permissions)
- **APPWRITE_FUNCTION_PROJECT_ID** - Your Appwrite Project Id ( can be found at `settings` tab on your Appwrite console)
- **DROPBOX_ACCESS_TOKEN** - Your Dropbox app access token (can be found in Drobox app console)

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/backup-to-dropbox
```

* Ensure that your folder structure looks like this 
```
.
├── mod.ts
├── README.md
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz backup-to-dropbox
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "deno run --allow-all mod.ts") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'


## Usage
* Manually:
  * Go to the Function **Overview** Tab
  * Click **Execute Now**
* Add function execution to schedule:
  * Go to the Function **Settings** Tab
  * Fill CRON schedule, for example `0 6 * * *` — will execute function every day at 6am
