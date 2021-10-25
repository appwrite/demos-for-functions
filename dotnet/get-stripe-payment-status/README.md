# 📧  Retrieving Payment Status from Stripe API

A sample .NET Cloud Function for retreiving payment status using Stripe API.

## 📝 Environment Variables
<!-- Tell the users of your Cloud function, what Environment Variables your function uses. Use the following format -->

* **STRIPE_API_TOKEN** - Stripe API secret token
* **APPWRITE_FUNCTION_DATA** - Provide the `stripePaymentId` you want to retrieve the status of

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/get-stripe-payment-status

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like this 
```
  get-stripe-payment-status -> ......\demos-for-functions\dotnet\get-stripe-payment-status\bin\Debug\net5.0\linux-x64\get-stripe-payment-status.dll
  get-stripe-payment-status -> ......\demos-for-functions\dotnet\get-stripe-payment-status\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dotnet get-stripe-payment-status.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

 Trigger the function using the SDK or HTTP API or the Appwrite Dashboard.
