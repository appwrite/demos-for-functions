# 📧 Add an email to SendGrid list

A sample Dart Cloud Function for adding the given email address to a Sendgrid contact list.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **SENDGRID_API_KEY** - Your Sendgrid API key
- **NEWSLETTER_LIST_ID** - List ID of the list the email should be added to.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dart/subscribe_to_send_grid

$ PUB_CACHE=.appwrite/ dart pub get
```

- Ensure that your folder structure looks like this

```
.
├── .appwrite/
├── main.dart
├── pubspec.lock
└── pubspec.yaml
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz subscribe_to_sendgrid
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "dart main.dart") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and click `Execute Now`. In the Custom Data field, input the mail address you want to add to the list.
This will cause the function to triggered whenever any user is created on the app, and their email will be added to the list specified.
