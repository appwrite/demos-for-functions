# 📧 getInternetSpeed
Python implementation to check the download and upload speed of the server

## 📝 Environment Variables
No environment variables required for this one


## 🚀 Building and Packaging

To package this as a cloud function, follow these steps.

**Install the SpeedTest CLI according to your distribution from [this link](https://www.speedtest.net/apps/cli)!**

```bash
$ cd demos-for-functions/dart/getInternetSpeed
$ dart pub get
```

* Ensure that your folder structure looks like this 
```
.
├── main.dart
└── pubspec.yaml
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf getInternetSpeed.tar.gz getInternetSpeed
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input  `dart main.dart` as the entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

Press Execute once deployed to test the Internet speed. 
Make sure to set the timeout above 45 seconds under the settings tab.