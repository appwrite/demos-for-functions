# 🗂 File Backup using the Dropbox API
A sample Python Cloud Function that leverages Dropbox to create backups of important files uploaded to Appwrite.

## 📝 Environment Variables
Add the following environment variables in your Cloud Function settings.

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.read`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **DROPBOX_KEY** - OAuth token from [Dropbox](https://blogs.dropbox.com/developers/2014/05/generate-an-access-token-for-your-own-account) 

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/object-detection

$ PIP_TARGET=./.appwrite pip install -r ./requirements.txt --upgrade --ignore-installed
```

* Ensure that your folder structure looks like this 
```
.
├── .appwrite/
├── main.py
└── requirements.txt
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz file-backup
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
python main.py
```

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `storage.files.create` event.