# 📧 Sending Welcome Emails using Mailgun's Email API
A sample Node.js Cloud Function for generating and saving a website screenshot in
the Appwrite storage.

This function takes url of a website as input, send it to CloudConvert CaptureWebsite API,
waits for the job to finish and saves newly created screenshot image into Appwrite Storage.
Finally, returns ID of the new file.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables. Note
that you need a valid Cloudconvert account with an API key.

* **CLOUDCONVERT_API_KEY** - API Key for Cloudconvert

## 📝 Custom Data (Parameters)
Add the following custom data when executing the Cloud Function.

* **url** - URL of the website to take the snapshot from
* **format** - (OPTIONAL, defaults to jpg) required image format. Must be a valid value
according to the Cloudconvert API (pdf, png or jpg).

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/generate-website-screenshot
$ npm install
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
├── node_modules/
├── package-lock.json
├── package.json
└── README.md
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz generate-website-screenshot
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case "node index.js") as your entrypoint command
* Upload your tarfile
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `storage.files.create` event.
