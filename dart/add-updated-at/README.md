# Add Updated At

A sample Dart Cloud Function that trigger on `database.documents.update` event and fill `updatedAt` attribute with the current unix timestamp (in seconds), if this rule for `updatedAt` is configured on a specific collection.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_ENDPOINT** - Your Appwrite Project Endpoint ( can be found at `settings` tab on your Appwrite console)
- **APPWRITE_FUNCTION_PROJECT_ID** - Your Appwrite Project Id ( can be found at `settings` tab on your Appwrite console)
- **APPWRITE_API_KEY** - Your Appwrite Project API Keys ( can be found at `API Keys` tab on your Appwrite console). Create a key with the scope (`documents.write`)

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps:

```bash
$ cd demos-for-functions/python/add-updated-at
$ export PUB_CACHE=.appwrite/
$ dart pub get
```

Ensure that your folder structure looks like this

```text
.
├── main.dart
├── .appwrite/
├── pubspec.lock
└── pubspec.yaml
```

Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz add-updated-at
```

Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
dart main.dart
```

## 💽 Database

Go to Database tab and follow these steps:

- Add new Collection, then database collection settings will popup
- At the Rules section, click `Add` 
- Fill `updatedAt` as the `Key` & `Label`
- Set the `Rule Type` to text and click `Create`
- Update the settings by clicking `Update` button
- Go to Documents tab and click `Add Document`
- Fill in the data needed and click create
- There should be `Document ID` and `Collection ID` on the right side (we need both of this as data when triggering cloud functions)

PS: You can add as many rules as wanted, we just need `updatedAt` key to see the cloud function woking

## 🎯 Trigger

- Head over to your function in the Appwrite console and under the Settings Tab, enable the `database.documents.update` event.
- See the result at Logs

## 📓 Note

- If `APPWRITE_ENDPOINT` is localhost, error might occur `Failed to connect to localhost/127.0.0.1:80)`, to test it locally try to use services like `ngrok` which provide tunneling to localhost.