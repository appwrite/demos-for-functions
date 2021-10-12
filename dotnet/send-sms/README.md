# 📲 Send SMS with Twilio Programmable Messaging

A sample .NET Cloud Function to send an SMS to another phone number

## 📝 Environment Variables

* **TWILIO_ACCOUNTSID** - Twilio Account SID
* **TWILIO_AUTHTOKEN** - Twilio Auth Token
* **TWILIO_PHONENUMBER** - Twilio Phone Number to send the SMS from

ℹ️ _The Twilio Account SID and Auth Token can be obtained from your Twilio console. You can purchase a Twilio phone number using [this guide](https://support.twilio.com/hc/en-us/articles/223135247-How-to-Search-for-and-Buy-a-Twilio-Phone-Number-from-Console)._

## 🚀 Building and Packaging

To package this example as a Cloud Function, follow these steps:

```bash
$ cd demos-for-functions/dotnet/send-sms

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like:

```
  SendSMS -> ......\demos-for-functions\dotnet\send-sms\bin\Debug\net5.0\linux-x64\SendSMS.dll
  SendSMS -> ......\demos-for-functions\dotnet\send-sms\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag and switch to the Manual tab
* Input the command that will run your function (in this case `dotnet SendSMS.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

_Note: In order to enable the .NET environment and runtime to be available in Appwrite Cloud Functions, you need to update the `.env` file in the Appwrite installation folder. Find the file and add `dotnet-5.0` to the comma-separated lists in the environment variables `_APP_FUNCTIONS_ENVS` and `_APP_FUNCTIONS_RUNTIMES`. This will make the .NET environment and runtime available in Appwrite Functions. You can then load the updated configuration using the `docker-compose up -d` command._

## 🎯 Trigger

You can trigger the Cloud Function using the SDK or HTTP API or the Appwrite Console.

Please include the data in the following format to properly trigger the function:

```json
{
	"toPhoneNumber": "+919876543210",
	"messageBody": "Testing Twilio Programmable SMS on a Appwrite Cloud Function"
}
```

* `toPhoneNumber` is the receiver's phone number in the format `[+][Country Code][Phone Number]`
* `messageBody` is the body of the message you want to send
