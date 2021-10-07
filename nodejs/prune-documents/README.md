# 🚮 Clean up documents in your database older than XX days

A sample NodeJS Cloud Function for deleting documents that are older than `XX` days on a schedule.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **API_ENDPOINT** - Your Appwrite Endpoint
- **API_SECRET** - Your Appwrite API key with `collections.read` and `collections.write` permissions
- **DAYS_TO_EXPIRE** - Days for document to expire

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/prune-documents

$ npm install
```

- Ensure that your folder structure looks like this

```
.
├── index.js
├── node_modules/
├── package-lock.json
└── package.json
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz prune-documents
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "node index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## ⏰ Schedule

Head over to your function in the Appwrite console and under the Settings Tab, enter a reasonable schedule time (cron syntax).

For example:

- `*/30 * * * *` every 30 minutes
- `0 * * * *` every hour
- `0 0 * * *` every day
