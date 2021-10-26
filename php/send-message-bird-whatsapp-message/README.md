# 📧  sendMessageBirdWhatsappMessage()
AppWrite Cloud Function to send WhatsApp Business messages through the [Message Bird](http://www.messagebird.com/en/)
API.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables:

* `APPWRITE_ENDPOINT`: Your Appwrite Endpoint.
* `APPWRITE_API_KEY`: Your Appwrite [API key](https://appwrite.io/docs/keys).
* `APPWRITE_FUNCTION_DATA`: Function-specific data in JSON format:

```json
{
    "MESSAGE_BIRD_ACCESS_KEY": "<message-bird-access-key>",
    "WHATSAPP_CHANNEL_ID": "<whatsapp-channel-id>",
    "WHATSAPP_TO": "<recipient-phone-number>",
    "TEXT": "<message body>", 
    "REPORT_URL": "<url to deliver status report for the message [HTTPS only]>"
}
```

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

### Package the function

```bash
$ cd demos-for-functions/php/send-message-bird-whatsapp-message
$ composer install
$ tar -zcvf code.tar.gz .
```

### Create Function through the Appwrite Console, Deploy the CLI
1. You can add your function from your Appwrite project's dashboard. [Click here](https://appwrite.io/docs/functions#addFunction) for more instructions. It's also possible to achieve this using the Appwrite CLI.
2. Deploy your function with the Appwrite CLI:
   ```bash
   appwrite functions createTag \
       --functionId=<functionId> \
       --command="php index.php" \
       --code="<code-directory>"
   ```

## 🎯 Trigger

- Manually trigger through the Appwrite console.
- Customise the function to set up event triggers with support for messaging on user-based events like account creation or even global events like scheduled maintenance.
