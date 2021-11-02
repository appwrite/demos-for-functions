# Create user profile upon a new user registration
A sample Python Cloud Function for creating a user profile document when a new user account is created

## 📝 Environment Variables
Add the following environment variables in your Cloud Functions settings.

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`users.read`, `documents.read`, `documents.write`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_PROJECT_ID** - Your Appwrite Project ID
* **APPWRITE_USER_COLLECTION_ID** - User collection ID

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/create_user_profile

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
$ tar -zcvf code.tar.gz create_user_profile
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
python main.py
```

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `account.create` event.