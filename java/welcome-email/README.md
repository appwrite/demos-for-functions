# 📧 Sending Welcome Emails using Mailgun's Email API
A sample Java Cloud Function for sending a welcome email to a newly registered user.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **MAILGUN_API_KEY** - API Key for Mailgun 
* **MAILGUN_DOMAIN** - Domain Name from Mailgun

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, Eclipse or any other IDE that has support for Java projects. 


* Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

* Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/java/welcome-email/out/artifacts/welcome_email_jar/welcome_email.jar`

```bash
$ cd demos-for-functions/java/welcome-email/out/artifacts/
$ tar -zcvf code.tar.gz welcome_email_jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar welcome-email.jar`) as your entry point command
* Upload your `tarfile` 
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.
