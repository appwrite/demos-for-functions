# Translate text from one language to another
A sample python Cloud Function for translating text from one language to another using [python translate package](https://github.com/terryyin/translate-python).


## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/translate-text

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
$ tar -zcvf code.tar.gz translate-text
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `python main.py`) as your entrypoint command
* Upload your tarfile
* Click 'Activate'

## Trigger
Go to your appwrite console and manually execute function with input in following format:
```json
{
  "text":ENTER TEXT THAT YOU WANT TO TRANSLATE,
  "source": ENTER ISO-LANGUAGE CODE OF SOURCE TEXT (OPTIONAL),
  "dest": ENTER ISO-LANGUAGE CODE TO WHICH YOU WANT TO TRANSLATE
}
```
> For ISO-language codes, see [this](https://www.andiamo.co.uk/resources/iso-language-codes/) and choose accordingly.
