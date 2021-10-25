# 🖼️ Sending SMS using Message Bird API
A sample Java Cloud Function for sending SMS using [Message Bird API](https://developers.messagebird.com/api/sms-messaging/).

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **MESSAGE_BIRD_API_KEY** - API Key for Message Bird
* **APPWRITE_FUNCTION_DATA** - phoneNumber and text

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, Eclipse or any other IDE that has support for Java projects.


* Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

* Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/java/send-message-bird-sms/out/artifacts/send_message_bird_sms_jar/send-message-bird-sms.jar`

```bash
$ cd demos-for-functions/java/send-message-bird-sms/out/artifacts/
$ tar -zcvf code.tar.gz send_message_bird_sms_jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar send-message-bird-sms.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'

## 🎯 Trigger

Trigger the cloud function using the SDK, HTTP API or the Appwrite Console.

- Sample Input: the recipient phone number and message text


```
{
    "phoneNumber": "+11234567890",
    "text": "test sms"
}
```

Sample response: the status of the SMS

```
MessageResponse{
id='dfa29a05726345c3a1809a119274e680', 
href='https://rest.messagebird.com/messages/dfa29a05726345c3a1809a119274e680', 
direction='mt', 
type=MsgType{value='sms'}, 
originator='MessageBird', 
body='test sms', 
reference='null', 
validity=null, 
gateway=10, 
typeDetails={}, 
datacoding=DataCoding{value='plain'}, 
mclass=MClass{value=1}, 
scheduledDatetime=null, 
createdDatetime=Fri Oct 08 16:03:34 GMT 2021, 
recipients=Recipients{
    totalSentCount=1, 
    totalDeliveredCount=0, 
    totalDeliveryFailedCount=0, 
    items=[
        Items{
            recipient=11234567890, 
            status='sent', 
            statusDatetime=Fri Oct 08 16:03:34 GMT 2021, 
            price=null}]
    }
}
```