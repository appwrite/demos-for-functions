# 📧  Prune documents
This function scans all documents in all collections to read their createdAt attribute. If a document has this attribute and it's older than 5 years, it removes the document. Function outputs individual document removals and count of deleted documents.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

Required:
* **APPWRITE_ENDPOINT** — Your Appwrite Endpoint
* **APPWRITE_API_KEY** — Your Appwrite API key with `collections.read`, `documents.read` and `documents.write` permissions

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, Eclipse or any other IDE that has support for Kotlin projects.

* Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

* Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/kotlin/prune-documents/out/artifacts/prune_documents_jar/prune-documents.jar`

```bash
$ cd kotlin/prune-documents/out/artifacts
$ tar -zcvf code.tar.gz prune_documents_jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar prune-documents.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'

## ⏰ Schedule

Head over to your function in the Appwrite console and under the Settings Tab, enter a reasonable schedule time (cron syntax).

For example:

- `*/30 * * * *` every 30 minutes
- `0 * * * *` every hour
- `0 0 * * *` every day
