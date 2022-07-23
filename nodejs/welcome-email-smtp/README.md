# 📧 Sending Welcome Emails using your own SMTP server
A sample Node.js Cloud Function for sending a welcome email to a newly registered user.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **SMTP_HOST** - Your SMTP host
* **SMTP_PORT** - Your SMTP host port
* **SMTP_USER** - Your SMTP username
* **SMTP_PASSWORD** - Your SMTP password
* **SMTP_FROM** - From who the email should come from

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/welcome-email-smtp

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
$ tar -zcvf code.tar.gz welcome-email-smtp
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case "node index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.
