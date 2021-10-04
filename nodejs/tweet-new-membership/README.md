# 📧 Tweeting about a new member using Twitter's API
A sample Node.js Cloud Function for sending a tweet when a new user joins.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **TWITTER_CONSUMER_KEY** - Consumer key of your app
* **TWITTER_CONSUMER_SECRET** - Consumer secret of your app
* **TWITTER_ACCESS_TOKEN_KEY** - Access token key of your app
* **TWITTER_ACCESS_TOKEN_SECRET** - Access token secret of your app

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/tweet-new-membership

$ npm install
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
├── node_modules/
├── package-lock.json
└── package.json
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz tweet-new-membership
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case "node index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.
Whenever a new user registers, this function will be fired and a tweet with the content "Welcome <name>!" will be sent. The output log for the function on the Appwrite dashboard will indicate if a tweet was sent out successfully, if not it will contain the error output. 