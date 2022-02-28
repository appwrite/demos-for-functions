# FCM Notification

A sample Dart Cloud Function that trigger on `database.documents.create` event and send a notification to an FCM (Firebase Cloud Messaging) topic.
It can be easily adapted to a specific user.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **FCM_SERVER_KEY** - Your FCM Server Key (can be found in Firebase Console / Project Settings / Cloud Messaging)


## 🚀 Building and Packaging
Edit in main.dart according to your needs:
```dart
const idPostCollection = 'post'; // Your collection
const idTitleAttribute = 'title'; // Your title attribut
const idBodyAttribute = 'body'; // Your body attribut
```

To package this example as a cloud function, follow these steps:

```bash
$ cd demos-for-functions/dart/fcm_notification
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
$ tar -zcvf code.tar.gz fcm_notification
```

Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
dart main.dart
```

## 💽 Database

Go to Database tab and follow these steps:

- Add new Collection, with the id you put in the `idPostCollection` variable
- Add `title` and `body` attributes
- Create a document

## 🎯 Trigger

- Head over to your function in the Appwrite console and under the Settings Tab, enable the `database.documents.create` event.
- See the result at Logs

## 📓 Note

- To receive notifications on flutter app, you need to use [firebase_messaging](https://pub.dev/packages/firebase_messaging) and [flutter_local_notifications](https://pub.dev/packages/flutter_local_notifications)
- Some help getting started [here](https://firebase.flutter.dev/docs/messaging/overview/)