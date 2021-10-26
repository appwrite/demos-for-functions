# 🚮 Backup all collections documents to S3
A sample Deno Cloud Function to backup all collections documents to S3

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_ENDPOINT** - Your Appwrite Project Endpoint
- **APPWRITE_API_KEY** - Your Appwrite API key with `collections.read` and `documents.read`
- **APPWRITE_FUNCTION_PROJECT_ID** - Your Appwrite Project Id 
- **AWS_ACCESS_KEY_ID** - Your AWS Access Key ID
- **AWS_SECRET_ACCESS_KEY** - Your AWS Secret Access Key
- **AWS_BUCKET** - Your AWS S3 Bucket name
- **AWS_REGION** - Your AWS S3 Bucket region

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/backup-to-s3
$ tar -zcvf code.tar.gz .
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `deno run --allow-env --allow-net mod.js`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## Trigger

Head over to your function in the Appwrite console and just press **Execute Now**. 
No Custom Data is needed

Example response:
```
Uploading Collection1-1635215045915.csv to S3
Uploaded Collection1-1635215045915.csv to S3
Uploading Collection2-1635215045915.csv to S3
Uploaded Collection2-1635215045915.csv to S3
```

## ⏰ Schedule

Head over to your function in the Appwrite console and under the Settings Tab, enter a reasonable schedule time (cron syntax).

For example:

- `*/30 * * * *` every 30 minutes
- `0 * * * *` every hour
- `0 0 * * *` every day