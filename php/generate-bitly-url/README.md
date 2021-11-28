# 📧  Shorten URL using Bitly API
This function takes an url as an input, generate a short URL using Bitly and output the shorten URL.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **BITLY_ACCESS_TOKEN** - Your Bitly access token

Your data must include the url:

### Example:
```url
https://appwrite.io
```


## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/php/generate-bitly-url

$ tar -zcvf code.tar.gz .
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `php index.php`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'


## 🎯 Trigger

Head over to your function in the Appwrite console and click on execute. Provide your link and continue.

For example: https://appwrite.io/