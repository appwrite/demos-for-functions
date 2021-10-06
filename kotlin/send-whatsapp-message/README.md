# 📲 Sending Whatsapp Message using Twilio
A sample Kotlin Cloud Function for sending Whatsapp Message

## 🚨 Ensure You Have Twilio Account and project
    - You can create Free Trial Account, and get $15 Credits [Free Trial Twilio](https://www.twilio.com/try-twilio?utm_source=google&utm_medium=cpc&utm_term=twilio&utm_campaign=Sitelink-G_S_Brand_Phrase_APAC_ID_EN_Brand)
    - Setup Your Development Sandbox refer to [Setup Sandbox]("https://www.twilio.com/docs/whatsapp/quickstart/curl")
    - For Production [Request to enable your Twilio numbers for WhatsApp]("https://www.twilio.com/whatsapp/request-access")

## ⚙️ Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **ACCOUNT_SID** - Twilio ACCOUNT SID
* **AUTH_TOKEN** - Twilio Auth Token
* **FROM_NUMBER** - Sender Phone Number (you can use Twilio Sandbox Number )


## 🏗 Build


* Import the project into IntelliJ or any other IDE that has support for Kotlin projects.
* or you can run gradlew script ``` ./gradlew build ```

* Create a tarfile

```bash
$ cd demos-for-functions/kotlin/appwrite-send-whatsapp-message/app/build/libs/
$ tar -zcvf app.tar.gz .
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar app.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'

## payload or request data


## Trigger
- you can invoke this function using execute button and fill JSON data
-  phone number format begining with country code like +62

```js
    {
        "phoneNumber":"+6281392944603",
        "text":"Hello I am Using AppWrite"
    }
  ```

- check the log


