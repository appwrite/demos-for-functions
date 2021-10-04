# 📧 Send SMS

Demo function to send SMS to a phone number using Twilio API.

## 📝 Environment Variables

* **TWILIO_ACCOUNT_SID** - Your Twilio account SID
* **TWILIO_AUTH_TOKEN** - Your Twilio account Authorization Token
* **TWILIO_SENDER** - Your Twilio phone number to send SMS from

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/send-sms
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz send-sms
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "deno run --allow-net --allow-env index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

You can trigger the function using the SDK, HTTP API, or the Appwrite Console.

Your data must include the following data:

```json
{
    "phoneNumber": "<receiver's phone number>",
    "text": "<message body>"
}
```

Example response:

```json
{
  "account_sid": "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
  "api_version": "2010-04-01",
  "body": "test",
  "date_created": "Mon, 4 Oct 2021 10:12:12 +0000",
  "date_sent": "Mon, 4 Oct 2021 10:12:14 +0000",
  "date_updated": "Mon, 4 Oct 2021 10:12:14 +0000",
  "direction": "outbound-api",
  "error_code": null,
  "error_message": null,
  "from": "+1XXXXXXXXXX",
  "messaging_service_sid": null,
  "num_media": "0",
  "num_segments": "1",
  "price": null,
  "price_unit": null,
  "sid": "SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
  "status": "sent",
  "subresource_uris": {
    "media": "/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Messages/SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Media.json"
  },
  "to": "+1XXXXXXXXXX",
  "uri": "/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Messages/SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.json"
}
```
