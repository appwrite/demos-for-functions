# 🖼️ Searching GIF by keyword using Giphy's API
A sample Java Cloud Function for searching a GIF by keyword using [Giphy's API](https://developers.giphy.com/docs/api#quick-start-guide).

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **GIPHY_API_KEY** - API Key for Giphy
* **APPWRITE_FUNCTION_DATA** - the search keyword

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, Eclipse or any other IDE that has support for Java projects. 


* Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

* Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/java/generate-giphy-gif/out/artifacts/generate_giphy_gif_jar/generate-giphy-gif.jar`

```bash
$ cd demos-for-functions/java/generate-giphy-gif/out/artifacts/
$ tar -zcvf code.tar.gz generate_giphy_gif_jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar generate-giphy-gif.jar`) as your entry point command
* Upload your `tarfile` 
* Click 'Activate'

## 🎯 Trigger

Trigger the cloud function using the SDK, HTTP API or the Appwrite Console.

- Sample Input: the search keyword

```
cat
```

Sample response: the first gif

```
https://giphy.com/gifs/cat-smoke-smoking-3o6Zt481isNVuQI1l6
```