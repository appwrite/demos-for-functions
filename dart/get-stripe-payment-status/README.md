# 💰 Get Payment Status From Stripe 
A sample Dart Cloud Function for getting payment status from stripe using transaction ID.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **STRIPE_KEY** - Your Stripe Secret Key, Obtained from the Stripe Dashboard

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dart/get-stripe-payment-status

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
$ tar -zcvf code.tar.gz get-stripe-payment-status
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dart main.dart`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🚨 Note

This function takes the paymentID parameter through the `APPWRITE_FUNCTION_DATA` environment variable therefore you would have to manually execute the file and supply the paymentID in the `CUSTOM DATA` field of functions executions.
