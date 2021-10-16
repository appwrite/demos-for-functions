# ➕Add created Timestamp to document

A sample Deno Cloud function to add `createdAt` value to the document if the rule is specified in the collection.

## 📝 Environment Variables

Define these in your .env file

- **APPWRITE_ENDPOINT** - Your API endpoint
- **APPWRITE_API_KEY** - Your secret API key

## ✅ Permissions

This script required the following permissions to run:

- net
- read
- env

(See example run command in the section below)

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.
Duplicate the .env.example file and name it .env, fill in the endpoint and api key.

```bash
$ cd demos-for-functions/deno/add-created-at
```

- Ensure that your folder structure looks like this

```
.
├── .env
└── index.js
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf add-created-at.tar.gz add-created-at
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "deno run --allow-env --allow-net --allow-read index.js") as your entrypoint command
- Flags --allow-env and --allow-read are required since this script will read from both the environment variables and the .env file
- Flag --allow-net is required because the script will access internet
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

In the function setting, check the `database.document.create` event. After that the function will automatically trigger when a new document is created.