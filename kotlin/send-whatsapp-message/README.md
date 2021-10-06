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

![Screen Shot 2021-10-06 at 13 40 17](https://user-images.githubusercontent.com/40946917/136160993-97febc7e-0252-4c13-8144-28dc4d2e50a6.png)



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


## Trigger
- you can invoke this function using execute button and fill JSON data
-  phone number format begining with country code like +62 like +628123456718

```js
    {
        "phoneNumber":"+628112345678",
        "text":"Hello I am Using AppWrite"
    }
  ```
  ![Screen Shot 2021-10-06 at 14 45 47](https://user-images.githubusercontent.com/40946917/136161356-5b7512f5-25e5-4c27-b437-3dd6601be0d9.png)





