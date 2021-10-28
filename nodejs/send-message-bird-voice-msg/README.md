# 📧 Send MessageBird Voice Message
A sample Node.js Cloud Function for sending a voice message using MessageBird. 

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **MESSAGE_BIRD_ACCESS_KEY** - API Key for MessageBird 

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/send-message-bird-voice-msg

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
$ tar -zcvf code.tar.gz send-message-bird-voice-msg
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case "node index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and click on execute. Provide `phoneNumber` and `text` in JSON format and continue.

For example:

- `{"text": "This is the message body", "phoneNumber": "xxx-xxx-xxx"}`
