# 📧  Generating Screenshot of Website using CloudConvert API
A sample Python Cloud Function for generating screenshot of website with given url and saving into AppWrite.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.write`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **CLOUDCONVERT_API_KEY** - API Key for CloudConvert
* **TARGET_URL** - Website URL to take screenshot
 

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/generate-website-screenshot

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
$ tar -zcvf code.tar.gz generate-website-screenshot
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
python main.py
```


## 🎯 Trigger
