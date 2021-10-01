# Translate text from one language to another
A sample python Cloud Function for translating text from one language to another using google translate API.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **GOOGLE_APPLICATION_CREDENTIALS** - Your google json API key path

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/translateText

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
$ tar -zcvf code.tar.gz translateText
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `python main.py`) as your entrypoint command
* Upload your tarfile
* Click 'Activate'

## Trigger
