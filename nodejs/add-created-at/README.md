# ➕Add created Timestamp to document

A sample NodeJS Cloud function to add `createdAt` value to the document if the rule is specified in the collection.

## 📝 Environment Variables

- **APPWRITE_ENDPOINT** - Your API endpoint
- **APPWRITE_PROJECT_ID** - Your project ID
- **APPWRITE_API_KEY** - Your secret API key

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/add-created-at
$ npm install
```

- Ensure that your folder structure looks like this

```
.
├── node_modules/
├── index.js
├── package-lock.json
└── package.json
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz add-created-at
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "node index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

In the function setting, check the `database.document.create` event. After that the function will automatically trigger when a new document is created.
