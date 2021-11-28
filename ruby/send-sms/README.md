# 📱 Send SMS with Twilio

A sample Ruby Cloud function to send SMS to other user's phone number.

## 📝 Environment Variables

- **TWILIO_ACCOUNT_SID** - Twilio account SID
- **TWILIO_AUTH_TOKEN** - Twilio auth token
- **TWILIO_SENDER** - Your Twilio phone number to send SMS from

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
    "receiver": "+15558675310",
    "message": "Hello world"
}
```

- `receiver` is the receiver's phone number
- `message` is the message you want to send

Example response:

```ruby
{
  "sid"=>"SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", 
  "date_created"=>"Thu, 14 Oct 2021 16:18:00 +0000", 
  "date_updated"=>"Thu, 14 Oct 2021 16:18:00 +0000", 
  "date_sent"=>nil, 
  "account_sid"=>"ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", 
  "to"=>"+15558675310", 
  "from"=>"+15005550006", 
  "messaging_service_sid"=>nil, 
  "body"=>"Hello world", 
  "status"=>"sent", 
  "num_segments"=>"1", 
  "num_media"=>"0", 
  "direction"=>"outbound-api", 
  "api_version"=>"2010-04-01", 
  "price"=>nil, 
  "price_unit"=>"USD", 
  "error_code"=>nil, 
  "error_message"=>nil, 
  "uri"=>"/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Messages/SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.json", "subresource_uris"=> {
    "media"=>"/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Messages/SMXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX/Media.json"
  }
}
```
