# Generate Giphy Gif  demo function

A sample Python Cloud Function for generating Giphy GIF from the [giphy API](https://developers.giphy.com/docs/api#quick-start-guide).

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **GIPHY_API_KEY** - Giphy GIF API Key

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/generate-giphy-gif

$ PIP_TARGET=./.appwrite pip install -r ./requirements.txt --upgrade --ignore-installed
```

* Ensure that your folder structure looks like this 
```
.
├── main.py
├── README.md
└── requirements.txt
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz generate-giphy-gif
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `python main.py`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and click on execute. Provide a query string. 

For example:
```
nft
```
