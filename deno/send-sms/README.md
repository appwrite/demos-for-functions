# ✉️ Send SMS

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

Your data must include the following fields:

```json
{
    "phoneNumber": "<receiver's phone number>",
    "text": "<message body>"
}
```
