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
```

You will first need to install the application's dependencies. You can do this manually using RubyGems, or you can install them using Bundler. To install via bundler, type:

```bash
$ bundle install
```

To install the gems manually (if you don't have bundler installed), type the following commands:

```bash
$ gem install twilio-ruby
```

- Ensure that your folder structure looks like this

```
.
├── main.rb
├── Gemfile
├── README.md
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