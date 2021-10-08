# 📧 Send Whatsapp Message through Message Bird

This function helps to send a whatsapp message from your Whatsapp Bussiness account to the given phone number by using MessageBird WhatsApp API and provide a json object as a response .

```json
{
  "id": "24500a370c86916fe8aef77e4c24b6a6",
  "status": "accepted",
  "fallback": null
}
```

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **MESSAGE_BIRD_API_KEY** - API key for MessageBird
- **WHATSAPP_CHANNEL_ID** - Channel Id of the whatsapp channel in MessageBird
- **REPORT_URL** - Deliver status reports to your platform through a POST request to a specific URL
  <br><br>
  > **Note** <br> If you don't plan to use REPORT*URL , make sure to remove the `reportUrl` json \_key-value* pair from `main.py`

![2021 10 08-12 40 32 screenshot](https://user-images.githubusercontent.com/63491234/136563554-946289f4-4eaf-4802-85a5-65e5da87178a.png)


## 📝 Custom Function Data

Go to `Execute Now` button , click it , then add the following custom data to the function

- **APPWRITE_FUNCTION_DATA** - It consists of JSON data which we need to send a message using the API. It takes the JSON as a string argument.

```json
{
  "PHONE_NUMBER": "<target-phone-number>",
  "TEXT ": "<whatsapp-text>"
}
```
![2021 10 08-18 33 38 screenshot](https://user-images.githubusercontent.com/63491234/136563782-e2c1587b-477c-4030-bf09-de50063c7104.png)

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

1. **Move to the functions directory**

```bash
$ cd demos-for-functions/python/send-message-bird-whatsapp-msg
```

2. To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/send-message-bird-whatsapp-msg

$ PIP_TARGET=./.appwrite pip install -r ./requirements.txt --upgrade --ignore-installed
```

3. Ensure that your folder structure looks like this

```
.
├── .appwrite/
├── main.py
└── requirements.txt
```

4. **Deploy to appwrite using Appwrite CLI**

```bash
appwrite functions createTag \
    --functionId=<functionId> \
    --command="python main.py" \
    --code="<code-directory>"
```

## 🎯 Trigger

- Head over to your function in the Appwrite console and Just press Excecute.
- You can modify the event triggers to fire at some particular event eg.( account creation , account email update , database document creation etc. )
- You can also set up cron job to fire the function at particular time eg.<br>
  ( 0 5 \* \* 0 ) `fires function at 5 pm every sunday to send a whatsapp message`
