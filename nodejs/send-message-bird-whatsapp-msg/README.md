# 📧 Sending custom Whatsapp Message through Messagebird

A sample Node.js Cloud Function for sending custom Whatsapp Message to a given phone number using Messagebird API.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_API_ENDPOINT** - Appwrite API endpoint
- **APPWRITE_PROJECT_ID** - Appwrite project's ID
- **APPWRITE_API_KEY** - Appwrite project's API key
- **MESSAGEBIRD_API_KEY** - Messagebird's API key
- **WHATSAPP_CHANNEL_ID** - Your Whatsapp channel ID

## JSON for function parameters

```
{
    "phone": RECIPIENT_PHONE_NUMBER,
    "text": TEXT_TO_SEND
}
```

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/send-message-bird-whatsapp-msg

$ npm install
```

- Ensure that your folder structure looks like this

```
.
├── index.js
├── .env
├── node_modules/
├── package-lock.json
└── package.json
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz send-message-bird-whatsapp-msg
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "node index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Overview Tab, click Execute Now, input the JSON and execute it.
