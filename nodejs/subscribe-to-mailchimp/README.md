# 📧 Subscribe to mailchimp newsletter using Mailchimp API V3
A sample nodejs Cloud Function for subscribing to a Mailchimp newsletter

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_MAILCHIMP_API_KEY** - Your Mailchimp API key
* **APPWRITE_MAILCHIMP_LIST_ID** - Your Mailchimp List ID

Your data must include the following field:

```json
{
    "email": "<email@appwrite.io>",
}
```

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/subscribe-to-mailchimp

$ npm install
```

* Create a tarfile

```bash
$ cd demos-for-functions/nodejs/subscribe-to-mailchimp
$ tar -zcvf code.tar.gz .
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case "node index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'
