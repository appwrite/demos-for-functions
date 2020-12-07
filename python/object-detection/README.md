# 📷 Object Detection using Cloudmersive Vision API
A sample Python Cloud Function for object detection on an image file uploaded by the user. 

## 📝 Environment Variables
Add the following environment variables in your Cloud Functions settings.

* **APPWRITE_KEY** - Create a key from the Appwrite console with the following scope (`files.read`)
* **API_KEY** - API Key for Cloudmersive 

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
$ tar -zcvf code.tar.gz object-detection
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
python main.py
```

## Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `storage.files.create` event.