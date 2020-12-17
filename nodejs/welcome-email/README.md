# 📧 Sending Welcome Emails using Mailgun's Email API
A sample Node.js Cloud Function for sending a welcome email to a newly registered user.
*Note: You will have to set up a [Mailgun account](https://www.mailgun.com/)*

## ☁️ Make a New Cloud Function
Navigate to 'Functions' and 'Add Function.'
Use 'Node.js 14.5' environment.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

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

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Deploy Tag
* Input the command that will run your function (in this case "node index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.
