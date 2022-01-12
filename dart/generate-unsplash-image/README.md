# 🔗 Generate Unsplash Image
A dart Cloud Function for searching image and author on unsplash from a keyword

## Dart Function Setup
To setup dart cloud function, follow [this excellent tutorial on Dev.to](https://dev.to/appwrite/learn-how-to-create-and-run-appwrite-functions-with-dart-5668)

Created using `dart-2.13`


## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variable.
****
* **UNSPLASH_ACCESS_KEY** -  API Key for Unsplash

## Sample Input
If running locally, add this when prompted for input on your `Appwrite Console` 

**input** : `cookies`

## Sample Output

**output**: `{"imageAuthor":"Food Photographer | Jennifer Pallian","imageUrl":"https://unsplash.com/photos/OfdDiqx8Cz8/download"}`

## 🚀 Building and Packaging

To package this as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dart/generate-unsplash-image

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
$ tar -zcvf code.tar.gz generate-unsplash-image
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dart main.dart`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'
