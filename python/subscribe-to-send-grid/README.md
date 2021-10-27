# 📧 Add an email to SendGrid list

A sample Python Cloud Function for adding the given email address to a Sendgrid Newsletter.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
- **APPWRITE_PROJECT_ID** - Appwrite project's ID
- **APPWRITE_API_KEY** - Your Appwrite Project's API key
- **SENDGRID_API_KEY** - Your Sendgrid API key
- **SENDGRID_LIST_ID** - List ID of the list the email should be added to.

You need to supply the email of user as parameter to the function in this format- { "email": EMAIL_ID }

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/subscribe-to-send-grid
$ pip install --target=./.appwrite -r ./requirements.txt --upgrade --ignore-installed
```

- Ensure that your folder structure looks like this

```
.
├── .appwrite/
├── main.py
└── requirements.txt
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz subscribe-to-sendgrid
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "python main.py") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Overview Tab, click Execute Now and supply the JSON.