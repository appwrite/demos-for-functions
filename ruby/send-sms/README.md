# 📱 Send SMS with Twilio

A sample Ruby Cloud function to send SMS to other user's phone number.

## 📝 Environment Variables

- **TWILIO_ACCOUNT_SID** - Twilio account SID
- **TWILIO_AUTH_TOKEN** - Twilio auth token

ℹ️ _Find your Account SID and Auth Token at twilio.com/console._

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/send-sms
$ docker run --rm -v $(pwd):/app -w /app --env GEM_HOME=./.appwrite appwrite/env-ruby-3.0:1.0.0 bundle install
```

- Ensure that your folder structure looks like this

```
.
├── .appwrite/
├── Gemfile
├── Gemfile.lock
├── main.rb
└── README.md
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz send-sms
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "ruby main.rb") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Trigger the cloud function using the SDK or HTTP API or the Appwrite Console.

You need to include the following data to properly trigger the function

```Json
{
    "sender": "+15017122661",
    "receiver": "+15558675310",
    "message": "The awesome message with lots of love you want to send"
}
```

- `sender` is the sender's phone number
- `receiver` is the receiver's phone number
- `message` is the message you want to send

Example response:

```json
{
  "account_sid": "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX",
  "api_version": "2010-04-01",
  "body": "Hi there",
  "date_created": "Thu, 04 Oct 2021 20:12:31 +0000",
  "date_sent": "Thu, 04 Oct 2021 20:12:33 +0000",
  "date_updated": "Thu, 04 Oct 2021 20:12:33 +0000",
  "direction": "outbound-api",
  "error_code": null,
  "error_message": null,
  "from": "+15017122661",
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
  "to": "+15558675310",
  "uri": "/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Messages/SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.json"
}
```
