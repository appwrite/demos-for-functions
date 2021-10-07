# 📧  Your Function Name
This function send a whatsapp message from your Whatsapp Bussiness account to teh given phone number. It leverages MessageBird WhatsApp API and provide status as an input (if the WhatsApp message was successfully sent or not). 

## 📝 Environment Variables

You must provide the below environment variable to execute the function:

* **APPWRITE_API_KEY** - Create a key from the Appwrite console.
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_FUNCTION_PROJECT_ID** - The id of the function from the Appwrite Console
* **APPWRITE_FUNCTION_DATA** - It consists of JSON data which we need to send a message using the API. It takes the JSON as a string argument.

```json
{
    "MESSAGE_BIRD_API_KEY":"<message-bird-api-key>",
    "WHATSAPP_CHANNEL_ID":"<whatsapp-channel-id>",
    "PHONE_NUMBER":"<receiver-phone-number>",
    "TEXT":"<text for the receiver>", 
    "REPORT_URL":"<returns the response to the respective url>",
}
```

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

1. **Move to the functions directory**

```bash
$ cd demos-for-functions/deno/send-message-bird-whatsapp-msg
```

2. **Add the .env file to the folder**
```bash
APPWRITE_ENDPOINT=""
APPWRITE_FUNCTION_PROJECT_ID=""
APPWRITE_API_KEY=""
APPWRITE_FUNCTION_DATA=""
```

3. **Add the import at top of the file to import environment variables**

```deno
import "https://deno.land/x/dotenv/load.ts";
```

4. **Run the function**
```bash
$ deno run --allow-env --allow-read --allow-net index.js
```

5. **Deploy to appwrite using Appwrite CLI**
```bash
appwrite functions createTag \
    --functionId=<functionId> \
    --command="deno run --allow-env --allow-net --allow-read index.js" \
    --code="<code-directory>"
```

## 🎯 Trigger

Head over to your function in the Appwrite console and Just press Excecute.
