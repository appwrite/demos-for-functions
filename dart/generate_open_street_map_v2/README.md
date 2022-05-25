# 🗂 Generate Open Street Maps
A sample Dart Cloud Function that saves an open street maps tile for given latitude and longitude to Appwrite storage.

## 📝 Environment Variables
Add the following environment variables in your Cloud Function settings.

* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.write`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **APPWRITE_BUCKET_ID** - ID of the bucket that you want to upload the map image to

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Ensure that your folder structure looks like this 
```
.
├── main.dart
├── pubspec.lock
└── pubspec.yaml
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz generate_open_street_map
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint

```bash
main.dart
```

## 🎯 Trigger

Head over to your function in the Appwrite console and after clicking the `Execute Now` button, enter a JSON object in the form of
```json
{
  "latitude": 37.7822403,
  "longitude": -122.3910414
}
```
with your respective coordinates.

If your function errors out with code 124, increase the timeout under the Settings Tab.