# ✉️ Send a message using Message Bird API
A Kotlin Cloud Function that sends a message using [MessageBird API](https://developers.messagebird.com/quickstarts/sms/send-sms-curl/)

## 📝 Environment Variables
When running function, pass data in the following format.
```json
{
  "phoneNumber": "<recipient's contact number with country code>",
  "text": "Hey There! From Appwrite"
}
```

- **MESSAGE_BIRD_LIVE_API_KEY** - Live API Key for Message Bird
- **ORIGINATOR_PHONE_NUMBER** - Your phone number which is used in Message Bird along with country code.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, or any other IDE that has support for Kotlin projects.

* Build a jar for the project. Follow the instructions [here](https://hardiksachan.hashnode.dev/build-a-jar-with-gradle)

* Create a tarfile

```bash
$ cd demos-for-functions/kotlin/send-message-bird-sms/build/libs/
$ tar -zcvf code.tar.gz ./send-message-bird-sms-1.0-SNAPSHOT.jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar send-message-bird-sms-1.0-SNAPSHOT.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'

## 🎯 Trigger
Can be triggered from manually from the Appwrite Console.