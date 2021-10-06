# 📧 Sending Welcome Emails using Mailgun's Email API

A Node.js Cloud Function for sending messages to users using phone number.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **MESSAGE_BIRD_LIVE_API_KEY** - Live API Key for Message Bird
- **ORIGINATOR_PHONE_NUMBER** - Your phone number which is used in Message Bird along with country code

Pass the phoneNumber and text as JSON data while executing the function

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/send-message-bird-sms

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
$ tar -zcvf code.tar.gz send-message-bird-sms
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "node index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and click on execute. After that, provide the phoneNumber and text in JSON format.
