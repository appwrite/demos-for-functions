# ➕Add created Timestamp to document

A sample Swift Cloud function to add creation datetime value to the document if the rule is specified in the collection.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_API_KEY** - Your Appwrite API key with `documents.write` permissions
* **CREATED_AT_KEY** - The document key for the created at timestamp. Defaults to `createdAt`

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/swift/add-created-at
docker run --rm -it -v $(pwd):/app -w /app swift:5.5 swift build -c release
```

* Ensure that your folder structure looks like this
```
├── .build/x86_64-unknown-linux-gnu/release/AddCreatedAt
├── Sources/
└── Package.swift
```

* Create a tarfile

```bash
$ tar -zcvf code.tar.gz -C .build/x86_64-unknown-linux-gnu/ release/AddCreatedAt
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `./AddCreatedAt`) as your entrypoint command
* Upload your tarfile
* Click 'Activate'

## 🎯 Trigger
In the function setting, check the `database.document.create` event. After that the function will automatically trigger when a new document is created.
