# 🖼️ Compress image using TinyPNG API and save the result into Appwrite Storage
A sample Java Cloud Function for compressing image using [TinyPNG API](https://tinypng.com/developers) and saving the result into Appwrite Storage.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_ENDPOINT** - Appwrite Endpoint.
* **APPWRITE_API_KEY** - Appwrite API key with `document.write` permission
* **TINYPNG_API_KEY** - API Key for TinyPNG API
* **APPWRITE_FUNCTION_DATA** - the image url

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, Eclipse or any other IDE that has support for Java projects. 


* Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

* Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/java/compress-tinypng-image/out/artifacts/compress_tinypng_image_jar/compress-tinypng-image.jar`

```bash
$ cd demos-for-functions/java/compress-tinypng-image/out/artifacts/
$ tar -zcvf code.tar.gz compress_tinypng_image_jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar compress-tinypng-image.jar`) as your entry point command
* Upload your `tarfile` 
* Click 'Activate'

## 🎯 Trigger

Trigger the cloud function using the SDK, HTTP API or the Appwrite Console.

- Sample Input: the search keyword

```
https://tinypng.com/images/example-orig.png
```

Sample response: the first gif

```
{
    "dateCreated": 1634143587,
    "signature": "f04b6966bd74409e0477d9c67d132c6d",
    "sizeOriginal": 15237,
    "name": "CompressTinyPngImage_1634143587290.png",
    "mimeType": "image/png",
    "$permissions": {
        "read": [],
        "write": []
    },
    "$id": "61670d63ac56f"
}
```
