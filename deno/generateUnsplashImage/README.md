# 📧  Get First Result from Unsplash
<!--  A brief descripption about your Cloud Function  -->
Get first result from **Unsplash** with given keyword.

## ☁️ Make a New Cloud Function
Navigate to 'Functions' and 'Add Function.'
Use 'Deno 1.5' environment.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_FUNCTION_DATA** - Keyword to be searched (string)
* **UNSPLASH_ACCESS_KEY** - Unsplash API Access Key (string)

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/deno/generateUnsplashImage
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
```
* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz generateUnsplashImage
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case "deno run --allow-net --allow-env index.js") as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger
Head over to your function in the Appwrite console and under the Setti