# 📧 get-internet-speed
Python implementation to check the download and upload speed of the server

## 📝 Environment Variables
No environment variables required for this one

## 🚀 Building and Packaging

To package this as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/get-internet-speed

$ pip install --target=./.appwrite -r ./requirements.txt --upgrade --ignore-installed 
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
$ tar -zcvf code.tar.gz get-internet-speed
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input  `python main.py` as the entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

Press Execute once deployed to test the Internet speed. 
Make sure to set the timeout above 45 seconds under the settings tab.
