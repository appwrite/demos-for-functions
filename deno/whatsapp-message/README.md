# 📧 Send Whatsapp Message

Demo function to send Wahtsapp message to a phone number using Twilio API.

## 📝 Environment Variables

* **TWILIO_ACCOUNT_SID** - Your Twilio account SID
* **TWILIO_AUTH_TOKEN** - Your Twilio account Authorization Token
* **TWILIO_FROM_NUMBER** - Your Twilio phone number to send Whatsapp message from, includes country code ! Ex: +5612345678

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/whatsapp-message
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz whatsapp-message
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
    "toNumber": "<receiver's phone number (includes your country code). Ex: +14155238886>",
    "messageBody": "<message body>"
}
```

Example response:

```json
{
  "account_sid": "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
  "api_version": "2010-04-01",
  "body": "Hello, there!",
  "date_created": "Thu, 30 Jul 2015 20:12:31 +0000",
  "date_sent": "Thu, 30 Jul 2015 20:12:33 +0000",
  "date_updated": "Thu, 30 Jul 2015 20:12:33 +0000",
  "direction": "outbound-api",
  "error_code": null,
  "error_message": null,
  "from": "whatsapp:+14155238886",
  "messaging_service_sid": "MGXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
  "num_media": "0",
  "num_segments": "1",
  "price": null,
  "price_unit": null,
  "sid": "SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
  "status": "sent",
  "subresource_uris": {
    "media": "/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Messages/SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Media.json"
  },
  "to": "whatsapp:+15005550006",
  "uri": "/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Messages/SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.json"
}
```