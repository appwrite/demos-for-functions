# 📧 Backup to Storage

A sample NodeJS Cloud Function for creating a backup of every document in every collection of your database in Cloud Storage.

## 📝 Environment Variables

Go to Settngs tab of your Cloud Function. Add the following enviromnent variables.

- **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
- **APPWRITE_API_KEY** - Your Appwrite API key with `collections.read`, `files.write`, `documents.read` permissions

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps

```bash
$ cd demos-for-functions/nodejs/backup-to-storage
$npm install
```

- Ensure that your folder structure looks like this

```
.
├── index.js
├── utils.js
├── node_modules/
├── package-lock.json
└── package.json
```

- create a tarfile using the command below:

```bash
$ cd ..

$ tar -zcvf code.tar.gz backup-to-storage
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tab(scroll down a bit)
- Input the command that will run when function is executed (in this case, "node index.js") as entrypoint command
- Switch to the manual tab and upload the tarfile
- Finally, click 'Activate'

## ⚡ Execution

Go the overview tab of your function and click 'Execute Now'. If you are in the functions menu, identify your function and click on the settings button.
Head over to logs and ensure that no errors were logged. A console log of 'backup created!' in `Output` means a backup of every document of every collection has been successfully created.
Head over to the storage tab and confirm that your csv backup has been created successfully.

## 🎯 Trigger

Now that we have confirmed that the function is working correctly, head over to the 'Settings' tab listed among the tabs 'Overview, Monitor and Logs', scroll down to the events section and enable the `database.documents.create` and `database.documents.update` events.
This will create a backup whenever a new document is created or an already existing document is updated.
