# 📧 Compress and Save image to Appwrite storage with given image url
A sample Dart Cloud Function to compress and save image to Appwrite storage with given image url.

## ☁️ Make a New Cloud Function
Navigate to 'Functions' and 'Add Function.'
Use 'Dart 2.13' environment.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **TINYPNG_API_KEY** - API Key for Tinypng 
* **API_ENDPOINT** - Appwrite API endpoint
* **API_SECRET** - Appwrite API Key
* **APP_FUNCTION_DATA** - Image URL to compress and save

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dart/compress-tinypng-image
```

* Ensure that your folder structure looks like this 
```
.
├── pubsec.yaml
└── compress.dart
```

* Compile code
```bash
$ dart pub get
$ dart compile exe compress.dart
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz compress-tinypng-image
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "./compress.exe") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger
Head over to your function in the Appwrite console and under the Settings Tab, enable relevant events or schedule.