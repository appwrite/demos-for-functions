# 🕒 Set Created At Timestamp on Document Creation

A sample Python Cloud Function that automatically sets a specified document field to the current unix timestamp (in seconds) on document creation.

## 📝 Environment Variables

Add the following environment variables in your Cloud Function settings.

- **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`documents.write`)
- **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
- **CREATED_AT_KEY** - The document key for the created at timestamp

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/add-created-at

$ PIP_TARGET=./.appwrite pip install -r ./requirements.txt --upgrade --ignore-installed
```

Ensure that your folder structure looks like this

```text
.
├── .appwrite/
├── main.py
└── requirements.txt
```

Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz add-created-at
```

Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
python main.py
```

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `storage.files.create` event.
