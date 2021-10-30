# 📷 Compress Image using TinyPNG API
A Kotlin Cloud Function that compresses image using [TinyPNG API](https://tinypng.com/developers)

## 📝 Environment Variables

- **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
- **APPWRITE_API_KEY** - Your Appwrite API key with `files.write` permissions
- **TINYPNG_API_KEY** - Your TinyPNG API key
- **APPWRITE_FUNCTION_DATA** - When running function, pass image url you want to compress and save as ***function data***.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, or any other IDE that has support for Kotlin projects.

* Build a jar for the project. Follow the instructions [here](https://hardiksachan.hashnode.dev/build-a-jar-with-gradle)

* Create a tarfile

```bash
$ cd demos-for-functions/kotlin/compress-tinypng-image/build/libs/
$ tar -zcvf code.tar.gz ./compress-tinypng-image-1.0-SNAPSHOT.jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar compress-tinypng-image-1.0-SNAPSHOT.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'

## 🎯 Trigger
Can be triggered from manually from the Appwrite Console.

## Output
The function outputs `id` of the file saved in Appwrite Storage.

## Sample Input And Output

#### Input
![image](https://user-images.githubusercontent.com/60518745/139032141-3ceff3a5-a362-4f00-8ebf-def9c67b5d31.png)

#### Output
![image](https://user-images.githubusercontent.com/60518745/139032118-2987ae76-51b0-400d-a7ed-94146e9e1b38.png)

