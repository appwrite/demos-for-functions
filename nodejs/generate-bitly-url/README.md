# 🔗 Generating shortened URLs using Bitly's developer API
A Node.js demo Cloud Function for generating a shortened URL using the Bitly API.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variable.

* **BITLY_ACCESS_TOKEN** - Bitly access token  

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/generate-bitly-url

$ npm install
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
├── node_modules/
├── package-lock.json
└── package.json
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz generate-bitly-url
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case "node index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

Trigger the function using the SDK or HTTP API or the Appwrite Dashboard.

The URL to be shortened should be passed to the Appwrite Function and is available to the function in an environment variable named `APPWRITE_FUNCTION_DATA`. This variable can be set only when triggering a function using the SDK or HTTP API and the Appwrite Dashboard.
After the function runs, it prints the shortened Bitly URL to `stdout`. This can be seen in the logs for the function on the Appwrite Dashboard.