# 📱 Sens SMS with Twilio

A sample Java Cloud Function to send SMS to other user's phone number.

## 📝 Environment Variables

- **TWILIO_ACCOUNT_SID** - Twilio account SID
- **TWILIO_AUTH_TOKEN** - Twilio auth token
- **TWILIO_NUMBER** - Your Twilio phone number to send SMS from

ℹ️ _Twilio account SID and auth token can be obtained from the twilio site itself._

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

- Import the project into IntelliJ, Eclipse or any other IDE that has support for Java projects.

- Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

- Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/java/send-sms/out/artifacts/send_sms_jar/send_sms.jar`

```bash
$ cd demos-for-functions/java/send-sms/out/artifacts/
$ tar -zcvf code.tar.gz send_sms_jar
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case `java -jar send-sms.jar`) as your entry point command
- Upload your `tarfile`
- Click 'Activate'

## 🎯 Trigger

Trigger the cloud function using the SDK or HTTP API or the Appwrite Console.

You need to include the following data to properly trigger the function

```Json
{
    "receiver": "+16502223333",
    "msg": "Appwrite Rocks 🤟🏻🤟🏻🤟🏻"
}
```

- `receiver` is the receiver's phone number
- `msg` is the message you want to send
