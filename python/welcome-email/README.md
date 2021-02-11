# 📧 Sending Welcome Emails using Mailgun's Email API
A sample Python Cloud Function for sending a welcome email to a newly registered user.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **MAILGUN_API_KEY** - API Key for Mailgun 
* **MAILGUN_DOMAIN** - Domain Name from Mailgun

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/welcome-email

$ virtualenv env

$ source env/bin/activate

$ pip3 install requirements.txt
```
Create a .env file with the following content

```
MAILGUN_API_KEY = 'Replace this with your key here'
MAILGUN_DOMAIN = 'Replace this with your Domain'

```

* Ensure that your folder structure looks like this 
```
.
├── main.py
├── env
└── requirements.txt
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case "python3 main.py") as your entrypoint command
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.
