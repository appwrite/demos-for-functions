# 📧 Send SMS using Message Bird
A sample Python Cloud Function for sending a SMS using Message Bird API

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **MESSAGE_BIRD_KEY** -  API Key for Message Bird
* **ORIGINATOR** - Name of Sender

Pass the `phoneNumber` and `text` as json into `APPWRITE_FUNCTION_DATA`

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/send-message-bird-sms

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
$ tar -zcvf code.tar.gz send-message-bird-sms
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `python main.py`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger
Trigger the function using the SDK or HTTP API or the Appwrite Dashboard.