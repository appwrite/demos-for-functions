# 🦠 Send A WhatsApp Message Using MessageBird

A sample Kotlin Cloud Function for sending a WhatsApp message using MessageBird.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **MESSAGEBIRD_ACCESS_KEY** - Mandatory. Your MessageBird Access Key. Created through Developers > API access on
  Dashboard.
- **WHATSAPP_CHANNEL_ID** - Mandatory. Your MessageBird WhatsApp Channel ID.
- **REPORT_URL** - Optional. The URL for delivering status updates. Must be HTTPS.
- **FALLBACK_CHANNEL_ID** - Optional. ID of MessageBird WhatsApp Channel to fallback to if the original message failed
  or could not be sent within a defined time window.
- **FALLBACK_WINDOW** - Optional. Duration to wait before triggering fallback. Defaults to 1 minute.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

- Create a tarfile

```bash
gradle shadowDistTar
```

(the tarfile is created in `build/distributions`)

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this
  case `java -jar lib/send-message-bird-whatsapp-msg-1.0.0-SNAPSHOT-all.jar`) as your entrypoint command
- Upload your tarfile
- Click `Activate`

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Overview Tab, click Execute Now and supply the function
data in JSON format, eg.

```json
{
  "phoneNumber": "+0123456789",
  "text": "Hello World"
}
```

### Parameters:

- **phoneNumber** - Mandatory. Phone number to send the message to.
- **text** - Mandatory. The plain-text content of the message.
- **source** - Optional. A JSON-formatted object that can be used to identify the source of the message.
- **tag** - Optional. Mark the message with a particular MessageBird Message Tag. The meaning and effect of each tag
  depend on each specific platform (
  see https://developers.messagebird.com/api/conversations/#messagetag-object
  for the list of options)
