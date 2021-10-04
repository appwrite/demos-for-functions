# Translate text from one language to another
A sample Dart Cloud Function for translating text from one language to another using google translate API.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **GOOGLE_APPLICATION_CREDENTIALS** - Your google json API key path
* **PROJECT_ID** - Your google cloud project id

### 📝 Input Format
Add the following to APPWRITE_FUNCTION_EVENT_DATA
* **text** - text to be translated
* **sourceLanguage** - source language from which text is to be translated in **BCP-47** format
* **destinationLanguage** - target language to which text is to be in **BCP-47** formattranslated

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dart/welcome_email

$ export PUB_CACHE=.appwrite/
$ dart pub get
```

* Ensure that your folder structure looks like this 
```
.
├── main.dart
├── .appwrite/
├── pubspec.lock
└── pubspec.yaml
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz translate_text
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input  `dart main.dart` as the entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

Press Execute once deployed to tranlate the text. 
Make sure to set the timeout above 45 seconds under the settings tab.
