# 📧 Subscribing to Sendgrid using SendGrid's API

A sample PHP Cloud Function to subsribe a new user to Sendgrid Newsletter.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **SENDGRID_API_KEY** - API Key for Sendgrid

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/php/subscribe-to-sendgrid

$ composer install
```

- Ensure that your folder structure looks like this

```
.
├── index.php
├── vendor/
├── composer.lock
└── composer.json
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz subscribe-to-sendgrid
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "php index.php") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.
