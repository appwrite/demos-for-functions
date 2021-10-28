# 🚮 Backup to Storage

A sample Kotlin Cloud Function to create a backup of collections and store it as csv in Appwrite Storage.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

-- **APPWRITE_ENDPOINT** - Appwrite Endpoint
-- **APPWRITE_FUNCTION_PROJECT_ID** - Appwrite Project Id
-- **APPWRITE_API_KEY** - Appwrite API Key

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

- Import the project into IntelliJ, Eclipse or any other IDE that has support for Kotlin projects.

- Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

- Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/kotlin/backup-to-storage/out/artifacts/backup_to_storage_jar/backup-to-storage.jar`

```bash
$ cd kotlin/backup-to-storage/out/artifacts
$ tar -zcvf code.tar.gz backup_to_storage_jar
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case `java -jar backup-to-storage.jar`) as your entry point command
- Upload your `tarfile`
- Click 'Activate'

## ⏰ Schedule

Head over to your function in the Appwrite console and under the Settings Tab, enter a reasonable schedule time (cron syntax).

For example:

- `*/30 * * * *` every 30 minutes
- `0 * * * *` every hour
- `0 0 * * *` every day
