# 📲 Send SMS with Twilio Programmable Messaging

A sample .NET Cloud Function to send an SMS to another phone number

## 📝 Environment Variables

Go to Settings tab of your Cloud Function and add the following environment variables:

* **TWILIO_ACCOUNTSID** - Twilio Account SID
* **TWILIO_AUTHTOKEN** - Twilio Auth Token
* **TWILIO_PHONENUMBER** - Twilio Phone Number to send the SMS from

ℹ️ _The Twilio Account SID and Auth Token can be obtained from your Twilio console. You can purchase a Twilio phone number using [this guide](https://support.twilio.com/hc/en-us/articles/223135247-How-to-Search-for-and-Buy-a-Twilio-Phone-Number-from-Console)._

## 🚀 Building and Packaging

To package this example as a Cloud Function, run the following commands in your Bash shell:

```bash
cd demos-for-functions/dotnet/send-sms

dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like:

```bash
  SendSMS -> ......\demos-for-functions\dotnet\send-sms\bin\Debug\net5.0\linux-x64\SendSMS.dll
  SendSMS -> ......\demos-for-functions\dotnet\send-sms\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag and switch to the Manual tab
* Input the command that will run your function (in this case `dotnet SendSMS.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

_Note: In order to enable the .NET environment and runtime to be available in Appwrite Cloud Functions, you need to update the `.env` file in the Appwrite installation folder. Find the file and add `dotnet-5.0` to the comma-separated lists in the environment variables `_APP_FUNCTIONS_ENVS` and `_APP_FUNCTIONS_RUNTIMES`. This will make the .NET environment and runtime available in Appwrite Functions. You can then load the updated configuration using the `docker-compose up -d` command._

## 🎯 Trigger

### Sample Input

You can trigger the Cloud Function using the SDK or HTTP API or the Appwrite Console.

Please include the data in the following format to properly trigger the function:

```json
{
	"phoneNumber": "+919876543210",
	"text": "Testing Twilio Programmable SMS on a Appwrite Cloud Function"
}
```

* `phoneNumber` is the receiver's phone number in the format `[+][Country Code][Phone Number]`
* `text` is the body of the message you want to send

### Sample Response

Here is a sample response that will be obtained after the successful execution of the function:

```json
{"body":"Testing Twilio Programmable SMS on a Appwrite Cloud Function","num_segments":"1","direction":"outbound-api","from":"+12184232045","to":"+919876543210","date_updated":"2021-10-17T05:51:51+00:00","price":null,"error_message":null,"uri":"/2010-04-01/Accounts/ACe29c144db149972dbf5427bbdd0c16dd/Messages/SMf4c30dd5c9594b34b36726bf63c75b45.json","account_sid":"ACe29c144db149972dbf5427bbdd0c16dd","num_media":"0","status":"queued","messaging_service_sid":null,"sid":"SMf4c30dd5c9594b34b36726bf63c75b45","date_sent":null,"date_created":"2021-10-17T05:51:51+00:00","error_code":null,"price_unit":"USD","api_version":"2010-04-01","subresource_uris":{"media":"/2010-04-01/Accounts/ACe29c144db149972dbf5427bbdd0c16dd/Messages/SMf4c30dd5c9594b34b36726bf63c75b45/Media.json"}}

```
