# 📷 Object Detection using Cloudmersive Vision API
A sample NodeJS Cloud Function for object detection on an image file uploaded by the user. 

## 📝 Environment Variables
Add the following environment variables in your Cloud Functions settings.

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.read`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **CLOUDMERSIVE_API_KEY** - API Key for Cloudmersive

## 🚀 Building and Packaging

To package this example as a Cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/object-detection

$ npm install
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
├── node_modules/
├── package-lock.json
└── package.json
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz object-detection
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
node index.js
```

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `storage.files.create` event.