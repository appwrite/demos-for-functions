# 📧 Welcome Email 
Sends the welcome email to the newly registered users to the app according to the template provided.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **MAILGUN_API_KEY** =  API Key for Mailgun
* **MAILGUN_DOMAIN** = Domain Name from Mailgun
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_PROJECT_ID** - Your Project ID
* **APPWRITE_API_KEY** - Your Appwrite API key with atleast `users.read` permissions

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/welcome-email

$ PIP_TARGET=./.appwrite pip install -r ./requirements.txt --upgrade --ignore-installed 
```

* Ensure that your folder structure looks like this 
```
.
├── .appwrite/
├── main.py
└── requirements.txt
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz welcome-email
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
python main.py
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `python main.py`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

When any user is created on the app, this function is automatically triggered. Allow events `account.create` and `users.create` from cloud function settings