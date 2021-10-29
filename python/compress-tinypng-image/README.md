# 🎴 Compressing the given image url and storing it to Appwrite
A sample Python Cloud Function that takes an image url, compress it using TinyPNG API and store it to Appwrite

## 📝 Environment Variables
Add the following environment variables in your Cloud Function settings.

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.write`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **TINIFY_KEY** - Your TinyPNG key. To generate the key, go to here -> https://tinypng.com/developers
* **IMAGE_URL** - Give the URL of the image for compression

## 🛠 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/compress-tinypng-image

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
$ tar -zcvf code.tar.gz compress-tinypng-image
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
python main.py
```

## 🎯 Trigger
Just press Excecute


