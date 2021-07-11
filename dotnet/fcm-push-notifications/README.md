# 🚮 Send push notification from FCM
A sample .NET Cloud Function for sending FCM Push Notifications to topics or tokens of choice

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **GOOGLE_APPLICATION_CREDENTIALS** - path to the private key file for the FCM project, if the file is in the root of the function the value should be just the name of file

## 🚀 Building and Packaging

To setup the example some additional steps are required so You will get the PN.

To get the FCM private key please use steps here:
https://firebase.google.com/docs/cloud-messaging/auth-server?hl=en#provide-credentials-manually
then put the file in fcm-push-notifications folder and rename it 'config.json' (as seen in csproj, You can keep the name and just change it there).

Its important that file will be added to the project and will be copied to output.

EITHER use a topic You registered the device to OR its token.
To get the token please paste the result of FirebaseMessaging.instance.getToken() into field
Otherwise the function will throw errors.

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/fcm-push-notifications

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like this 
```
  FCMPushNotifications -> ......\demos-for-functions\dotnet\fcm-push-notifications\bin\Debug\net5.0\linux-x64\FCMPushNotifications.dll
  FCMPushNotifications -> ......\demos-for-functions\dotnet\fcm-push-notifications\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dotnet FCMPushNotifications.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'
