# 🚮 Clean up files in your storage older than XX days
A sample .NET Cloud Function for deleting files that are older than XX days on a schedule.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_PROJECT_ID** - Your Project ID
* **APPWRITE_API_KEY** - Your Appwrite API key with `files.read` and `files.write` permissions
* **DAYS_TO_EXPIRE** - Days for files to expire

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/storage-cleaner

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like this 
```
  StorageCleaner -> ......\demos-for-functions\dotnet\storage-cleaner\bin\Debug\net5.0\linux-x64\StorageCleaner.dll
  StorageCleaner -> ......\demos-for-functions\dotnet\storage-cleaner\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dotnet StorageCleaner.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## ⏰ Schedule

Head over to your function in the Appwrite console and under the Settings Tab, enter a reasonable schedule time (cron syntax).

For example:

- `*/30 * * * *` every 30 minutes
- `0 * * * *` every hour
- `0 0 * * *` every day
