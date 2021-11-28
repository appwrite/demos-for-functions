# 🎴 Compressing the given image url and storing it to Appwrite
A sample Python Cloud Function that takes an image url, compress it using TinyPNG API and store it to Appwrite

## 📝 Environment Variables
Add the following environment variables in your Cloud Function settings.

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.write`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **ACCESS_TOKEN** - Your Access Key ID. You can get the key by going to your aws console and selecting `My Secret Credentials`.
* **ACCESS_KEY_SECRET** - Your AWS Secret Key. You can get it from the downloaded csv file or create a new key and copy it from there.
* **BUCKET_ID** - The bucket name in where you want to store the backup files.

## 🛠 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/backup-to-S3

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
$ tar -zcvf code.tar.gz backup-to-S3
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
python main.py
```

## 🎯 Trigger
Just press Excecute


