# Generate Giphy Gif
Retrieves a URL of a GIF for a searched topic

## ☁️ Make a New Cloud Function
Navigate to 'Functions' and 'Add Function.'
Use 'Deno 1.5' environment.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **GIPHY_API_KEY** - Your GIPHY Account's API Key
* **APPWRITE_FUNCTION_EVENT_DATA** - Must be a JSON. Takes a ```keyword``` property used for search

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/generate-giphy-gif
```

* Ensure that your folder structure looks like this
```
.
├── index.js
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz generate-giphy-gif
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "deno run --allow-env --allow-net index.js") as your entrypoint command
* Upload your tarfile
* Click 'Activate'

## 🎯 Trigger
Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.
