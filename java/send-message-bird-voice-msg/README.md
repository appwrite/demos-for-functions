# 📧 Sending Voice Message using MessageBird Voice Messaging API
A sample Java Cloud Function for sending a voice message using MessageBird Voice Messaging API.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **MESSAGEBIRD_API_KEY** - API Key for MessageBird

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, Eclipse or any other IDE that has support for Java projects.

* Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

* Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/java/send-message-bird-voice-msg/out/artifacts/send_message_bird_voice_msg_jar/send-message-bird-voice-msg.jar`

```bash
$ cd demos-for-functions/java/send-message-bird-voice-msg/out/artifacts/
$ tar -zcvf code.tar.gz send_message_bird_voice_msg_jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar send-message-bird-voice-msg.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and click on execute. Provide phoneNumber and text in JSON format and continue.

For example:
{"phoneNumber": "1234567890", "text": "Hey there! This is a test message." }