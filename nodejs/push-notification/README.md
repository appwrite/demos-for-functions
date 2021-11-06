# 📧 Sending Push Notifications using Firebase
A sample Node.js Cloud Function for sending a simple push notification over firebase to a mobile device

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/push-notification

$ npm install
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
├── node_modules/
├── package-lock.json
├── package.json
└── serviceAccountKey.json (generated in firebase)
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz push-notification
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case "node index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'
