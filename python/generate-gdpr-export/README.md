# 📷 GDPR Export Function 
A sample Python Function for exporting documents to csv format and saving it to the user storage. 

## 📝 Environment Variables
Add the following environment variables in your Cloud Functions settings.

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`collections.read, documents.read,files.write`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint


## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/generate-gdpr-export

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
$ tar -zcvf code.tar.gz generate-gdpr-export
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
python main.py
```

## 🎯 Trigger

To trigger this function you will need to create an execution by means of the WebSDK in order to get the user context.