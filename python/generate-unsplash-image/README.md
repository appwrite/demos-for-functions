# Get image from unsplash
A sample python Cloud Function for getting image url and image author from unsplash based on keyword search.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **UNSPLASH_ACCESS_KEY** - Your Unsplash Access Key for public authorization

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/generate-unsplash-image

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
$ tar -zcvf code.tar.gz generate-unsplash-image
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `python main.py`) as your entrypoint command
* Upload your tarfile
* Click 'Activate'

## Trigger
You can manually trigger function in the Appwrite console by clicking on execute and providing a keyword for querying or you can use an appwrite SDK.
