# 💵 Retreiving payment status using Stripe API
A sample Deno Cloud Function for retreiving payment status using Stripe API.

## ☁️ Make a New Cloud Function
Navigate to 'Functions' and 'Add Function.'
Use 'Deno 1.8' environment.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **STRIPE_API_TOKEN** - Stripe API secret token  

Provide the `stripePaymentId` you want to retrieve the status of as the `APPWRITE_FUNCTION_DATA` environment variable.

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/get-stripe-payment-status
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz get-stripe-payment-status
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "deno run --allow-net --allow-env index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger
Trigger the function using the SDK or HTTP API or the Appwrite Dashboard.