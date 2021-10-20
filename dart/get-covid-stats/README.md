# 🦠 Get Covid-19 stats from a third party API
A sample Dart Cloud Function for fetching Covid data from a third party API, [Covid19Api](https://covid19api.com/).

## 📝 Environment Variables
When running function, pass country code as function data.

* **APPWRITE_FUNCTION_DATA** - Code of the country in the ISO2 format, you want the statistics for. For example `IN`, `US`, `DE` etc. If unset, global stats will be returned.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dart/get-covid-stats
$ export PUB_CACHE=.appwrite/
$ dart pub get
```

* Ensure that your folder structure looks like this 
```
.
├── .appwrite
├── main.dart
├── pubspec.lock
└── pubspec.yaml
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz get-covid-stats
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case "dart main.dart") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger
Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.
