# 📧 Sending Welcome Emails using Mailgun's Email API
A sample .NET Cloud Function for sending a welcome email to a newly registered user.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **MAILGUN_API_KEY** - API Key for Mailgun 
* **MAILGUN_DOMAIN** - Domain Name from Mailgun

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/welcome-email

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like this 
```
  welcome-email -> ......\demos-for-functions\dotnet\welcome-email\bin\Debug\net5.0\linux-x64\welcome-email.dll
  welcome-email -> ......\demos-for-functions\dotnet\welcome-email\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ cd ..
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dotnet welcome-email.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.
