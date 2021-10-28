# 🚮 Subscribing to Sendgrid using SendGrid's API

A sample Kotlin Cloud Function to subsribe a new user to Sendgrid Newsletter.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

-- **SENDGRID_API_KEY** - API Key for Sendgrid

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

- Import the project into IntelliJ, Eclipse or any other IDE that has support for Kotlin projects.

- Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

- Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/kotlin/storage-cleaner/out/artifacts/storage_cleaner_jar/storage-cleaner.jar`

```bash
$ cd kotlin/storage-cleaner/out/artifacts
$ tar -zcvf code.tar.gz storage_cleaner_jar
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case `java -jar storage-cleaner.jar`) as your entry point command
- Upload your `tarfile`
- Click 'Activate'

## ⏰ Schedule

Head over to your function in the Appwrite console and under the Settings Tab, enter a reasonable schedule time (cron syntax).

For example:

- `*/30 * * * *` every 30 minutes
- `0 * * * *` every hour
- `0 0 * * *` every day
