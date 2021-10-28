# 🔗 Generate shorter URL from Bitly

Function to generate shorter URL address from address via [Bitly](https://bitly.com)

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_FUNCTION_DATA** - URL Addres to generate
* **BITLY_ACCESS_TOKEN** - Bitly bearer token

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, Eclipse or any other IDE that has support for Java projects. 


* Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

* Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `java/generate-bitly-url/target/GenerateBitlyURL-1.0-SNAPSHOT.jar`

```bash
$ cd java/generate-bitly-url/
$ tar -zcvf code.tar.gz target
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar GenerateBitlyURL-1.0-SNAPSHOT.jar`) as your entry point command
* Upload your `tarfile` 
* Click 'Activate'

## 🎯 Trigger

You trigger the function by manually executing it in the Appwrite console (Excetute Now) or by using SDK (excetuteFunction).
