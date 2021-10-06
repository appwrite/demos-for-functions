# Tweet when a new member joins a team
A python Cloud Function to tweet welcome msg for the new team members when they join

NOTE : You'll require to have a Twitter developer account to get the Twitter API!

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **TWITTER_KEY** - Your Twitter API key
* **TWITTER_SECRET** - Your Twitter API secret
* **TWITTER_access_token** - Your Twitter API access token
* **TWITTER_access_token_secret** - Your Twitter API access token secret

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/tweet-new-membership

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
$ tar -zcvf code.tar.gz tweet-new-membership
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `python main.py`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'
