# GIPHYGif Generator

A sample Dart Cloud Function which takes a keyword and returns the first result with giphy gif url

## Getting Started

Head over to the [Giphy Developers](https://developers.giphy.com/docs/api) to read their quick start guide and get your API key. Make sure to use the **GIPHY API** and not the **GIPHY SDK**.
<img src = 'https://github.com/2002Bishwajeet/demos-for-functions/blob/feat-implement-generate-giphy-gif-dart/dart/generate-giphy-gif/screenshots/SDKvsAPI.png' width = '100%' height = '50%' />

Since we are retrieving an url from the API, the GIPHY API is more than enough for our needs.

## ☁️ Create a new Function

**NOTE:** If your project is not created yet, you will be prompted to create a new project.

1. Go to your `appwrite console`
2. Select your project and go to `Functions`
<img src = 'https://github.com/2002Bishwajeet/demos-for-functions/blob/feat-implement-generate-giphy-gif-dart/dart/generate-giphy-gif/screenshots/functions.png' width = '100%' />
3. Under `Functions` click on `Add Function`
<img src ='https://github.com/2002Bishwajeet/demos-for-functions/blob/feat-implement-generate-giphy-gif-dart/dart/generate-giphy-gif/screenshots/function2.png' width = '100%' />

**NOTE:** If you don't see the Dart option in the runtimes. Follow [`troubleshooting.md`](troubleshoot.md) guide to learn how to add the Dart runtime.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variable.

* **GIPHY_API_KEY** - API Key of your GIPHY account

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
cd demos-for-functions/dart/generate-giphy-gif

PUB_CACHE=./.appwrite dart pub get
```

* Ensure that your folder structure looks like this

```
.
├── main.dart
├── .appwrite
├── pubspec.lock
└── pubspec.yaml
```

* Create a tarfile

```bash
cd ..
tar -zcvf code.tar.gz generate-giphy-gif
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case "dart main.dart") as your entrypoint command
* Upload your tarfile
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and just press **Execute**. You will notice the output in your logs


## 👨‍💻 Live Working 

<img src = "screenshots\ans.gif"/>