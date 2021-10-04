# 🔗 Shorten Long Link
A dart Cloud Function for generating a bitly short link from a given url.

## Dart AW Setup
To setup dart cloud function, follow [this excellent tutorial on Dev.to](https://dev.to/appwrite/learn-how-to-create-and-run-appwrite-functions-with-dart-5668)

Created using `dart-2.1.3`

## 🚀 Result
![result](docs-images/result.png)
<br>
![result log](docs-images/result-log.png)
<br>

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variable.
****
* **BITLY_TOKEN** -  API Key for Bitly


## Bitly Account
If you do not have an account already, [register here](https://bitly.com/pages/pricing/v2) for free.
<br>
Once registered, login and go on your dashboard, from there click your account name on the top right corner and go to `settings`
<br>
Under `settings`, scroll to `Developer settings` and click on `API`
<br>
Under this tab, notice the `Access token` menu, enter your account password you used to login / register and client `Generate token`
<br>
After a success, copy the token and paste on your AW console variables as `BITLY_TOKEN`
<br>
![bitly.png](docs-images/bitly.png)
<br>
<br>

## 🚀 Building and Packaging

To package this as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dart/generate-bitly-url

$ export PUB_CACHE=.appwrite/
$ dart pub get
```

* Ensure that your folder structure looks like this 
```
.
├── main.dart
├── .appwrite/
├── pubspec.lock
└── pubspec.yaml
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz generate-bitly-url
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dart main.dart`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'
