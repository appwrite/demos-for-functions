# 📧 Sending SMS with Message bird API

A Deno Cloud Function for sending messages to users using phone number.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **MESSAGE_BIRD_API_KEY** - Live API Key for Message Bird
- **MESSAGE_BIRD_FROM** - Your phone number which is used in Message Bird along with country code

Pass the phoneNumber and text as JSON data while executing the function

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/bird-message
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz bird-message
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "deno run --allow-net --allow-env index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and click on execute. After that, provide the phoneNumber and text in JSON format.

Your data must include the following data:

```json
{
    "toNumber": "<receiver's phone number (includes your country code). Ex: +14155238886>",
    "messageBody": "<message body>"
}
```

Example response:

```json
{
  "id": "e60a6f90453a19019c56ed6b20170831",
  "href": "https://rest.messagebird.com/messages/e60a6f90453a19019c56ed6b20170831",
  "direction": "mt",
  "type": "sms",
  "originator": "MessageBird",
  "body": "This is a test message.",
  "reference": null,
  "validity": null,
  "gateway": 240,
  "typeDetails": {},
  "datacoding": "plain",
  "mclass": 1,
  "scheduledDatetime": null,
  "createdDatetime": "2016-05-03T14:26:57+00:00",
  "recipients": {
    "totalCount": 1,
    "totalSentCount": 1,
    "totalDeliveredCount": 0,
    "totalDeliveryFailedCount": 0,
    "items": [
      {
        "recipient": 31612345678,
        "status": "sent",
        "statusDatetime": "2016-05-03T14:26:57+00:00",
      },
    ]
  }
}
```