# 📷 Generate GIF using GIPHY API
A cloud function to generate a GIF using [GIPHY API](https://developers.giphy.com/docs/api/#quick-start-guide)

## 📝 Environment Variables
When running function, **pass** keyword you want to search for as ***function data***. This should not be empty

* **GIPHY_API_KEY** - Your GIPHY API Key for public authorization

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, or any other IDE that has support for Kotlin projects.

* Build a jar for the project. Follow the instructions [here](https://hardiksachan.hashnode.dev/build-a-jar-with-gradle)

* Create a tarfile

```bash
$ cd demos-for-functions/kotlin/generate-giphy-gif/build/libs/
$ tar -zcvf code.tar.gz .\generate-giphy-gif-1.0-SNAPSHOT.jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar generate-giphy-gif-1.0-SNAPSHOT.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'

## 🎯 Trigger
Can be triggered from manually from the Appwrite Console.