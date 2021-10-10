# 📧  backupToDropbox
<!--  A brief descripption about your Cloud Function  -->
This functions reads all the collection from Appwrite and then all the documents
off the collections and uses csv-strigify a single document per row.

## 📝 Environment Variables
<!-- Tell the users of your Cloud function, what Environment Variables your function uses. Use the following format -->

* **APPWRITE_API_ENDPOINT** - The API endpoint from the AppWrite console
* **APPWRITE_API_KEY** - The API key from the AppWorite console.
* **DROPBOX_ACCESS_TOKEN** - Dropbox Access Token from Dropbox App Console


## 🚀 Building and Packaging

To package this as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/backupToDropbox
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
$ tar -zcvf code.tar.gz backupToDropbox
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "node index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger