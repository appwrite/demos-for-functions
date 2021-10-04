# 🔗 Generating shortened URLs using Bitly's developer API
A sample Deno Cloud Function for generating a shortened URL using the Bitly API.

## ☁️ Make a New Cloud Function
Navigate to 'Functions' and 'Add Function.'
Use 'Deno 1.5' environment.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **BITLY_ACCESS_TOKEN** - Bitly access token  

Provide the `url` you want to shorten as the `APPWRITE_FUNCTION_DATA` environment variable.

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/generate-bitly-url
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz generate-bitly-url
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "deno run --allow-net --allow-env index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger
Trigger the function using the SDK or HTTP API or the Appwrite Dashboard.