# 📷 Object Detection using Cloudmersive Vision API
A sample NodeJS Cloud Function for object detection on an image file uploaded by the user.

## 📝 Environment Variables
For this Cloud Function to work, these environement variables need to be added in the function settings

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.read`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_PROJECT_ID** - Your Project ID
* **CLOUDMERSIVE_API_KEY** - API Key for Cloudmersive (Note : create a free account on the cloudmersive website and generate your API key)

## 🚀 Building and Packaging

To package this example as a Cloud Function, follow these steps.

* Install dependencies
```bash
$ cd demos-for-functions/nodejs/object-detection
$ npm install
```

* If the install went well, the directory should look like this
```
.
├── node_modules
├── index.js
├── package.json
└── package-lock.json
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
