# 📧 Sending Welcome Emails using Mailgun's Email API
A sample Nodejs Cloud Function for sending a welcome email to a newly registered user.

## 📝 Environment Variables
Add the following environment variables in your Cloud Functions settings.

* **MAILGUN_API_KEY** - API Key for Mailgun 
* **MAILGUN_DOMAIN** - Domain Name from Mailgun

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/welcome-email

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
$ tar -zcvf code.tar.gz welcome-email
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
node index.js
```

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.