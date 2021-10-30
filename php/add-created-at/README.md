# Add createdAt Timestamp when document created

A sample PHP Cloud Function that trigger on `database.documents.create` event and fill `createdAt` attribute with the current time, if this rule for `createdAt` is configured on a specific collection.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_ENDPOINT** - Your Appwrite Project Endpoint ( can be found at `settings` tab on your Appwrite console)
- **APPWRITE_API_KEY** - Your Appwrite Project API Keys ( can be found at `API Keys` tab on your Appwrite console)

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/php/add-created-at

$ tar -zcvf code.tar.gz .
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case `php index.php`) as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

- Head over to your function in the Appwrite console, then add new function with PHP Runtimes
- under created functions Settings Tab, scroll down to variables section and add all the Environment Variables above.
- check the `database.document.create` event. After that the function will automatically trigger when a new document is created (see the Database section to learn how to create & update document).

## 💽 Database

Go to Database tab and follow these steps:

- Add new Collection, then database collection settings will popup
- At the Rules section, click add
- fill `createdAt` as the key & label
- set the Rule Type to text and click create
- update the settings by clicking update button
- Go to Documents tab and click add document
- fill in the data needed and click create
- after you create a document. the create button will convert to update button
- fill new data and click update and the cloud function will trigger

PS: You can add as many rules as wanted, we just need `createdAt` key to see the cloud functions working

## 📓 Note

- if `APPWRITE_ENDPOINT` is localhost, error will occur `Fatal error: Uncaught Appwrite\AppwriteException: Failed to connect to localhost port 80: Connection refused in /usr/local/src/vendor/appwrite/appwrite/src/Appwrite/Client.php:225`. It happenedd because 127.0.0.1 is not recognized, use your current network ip instead (by using `ipconfig` on windows, or see your network preferences on mac).