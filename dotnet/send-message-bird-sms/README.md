# 📧  Send SMS using Message Bird

A sample .NET Cloud Function for ending a SMS using Message Bird API.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

* **MESSAGE_BIRD_ACCESS_KEY** -  API Key for Message Bird
* **ORIGINATOR** - Name of Sender

Pass the `phoneNumber` and `text` as json into `APPWRITE_FUNCTION_DATA`


## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/send-message-bird-sms

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like this 
```
  send-message-bird-sms -> ......\demos-for-functions\dotnet\send-message-bird-sms\bin\Debug\net5.0\linux-x64\send-message-bird-sms.dll
  send-message-bird-sms -> ......\demos-for-functions\dotnet\send-message-bird-sms\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dotnet send-message-bird-sms.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

 Trigger the function using the SDK or HTTP API or the Appwrite Dashboard.
