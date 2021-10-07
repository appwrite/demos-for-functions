# 📧 Add an email to SendGrid list

A sample NodeJS Cloud Function for adding the given email address to a Sendgrid contact list.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
- **APPWRITE_API_KEY** - Your Appwrite API key with `files.read` and `files.write` permissions
- **SENDGRID_API_KEY** - Your Sendgrid API key
- **NEWSLETTER_LIST_ID** - List ID of the list the email should be added to.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/subscribe-to-sendgrid

$ npm install
```

- Ensure that your folder structure looks like this

```
.
├── index.js
├── node_modules/
├── package-lock.json
└── package.json
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz subscribe-to-snedgrid
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "node index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings tab, enable the `users.create` and `account.create` event.
This will cause the function to triggered whenever any user is created on the app, and their email will be added to the list specified.
