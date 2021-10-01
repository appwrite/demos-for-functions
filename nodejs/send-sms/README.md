# 📱 Send SMS with Twilio

A sample NodeJS Cloud function to send SMS to other user's phone number.

## 📝 Environment Variables

- **TWILIO_ACCOUNT_SID** - Twilio account SID
- **TWILIO_AUTH_TOKEN** - Twilio auth token
- **TWILIO_NUMBER** - Your Twilio phone number to send SMS from

ℹ️ _Twilio account SID and auth token can be obtained from the twilio site itself._

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/send-sms

$ npm install
```

- Ensure that your folder structure looks like this

```
.
├── node_modules/
├── index.js
├── package-lock.json
└── package.json
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz send-sms
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "node index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Trigger the cloud function using the SDK or HTTP API or the Appwrite Console.

You need to include the following data to properly trigger the function

```Json
{
    "receiver": "+16502223333",
    "message": "The awesome message with lots of love you want to send"
}
```

- `receiver` is the receiver's phone number
- `message` is the message you want to send
