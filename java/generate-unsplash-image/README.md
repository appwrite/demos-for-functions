# Generate Image From Unsplash API

A sample Java Cloud Function for generating picture url from Unsplash API.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **UNSPLASH_ACCESS_KEY** - Your Unsplash Access Key for public authorization

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

- Import the project into IntelliJ, Eclipse or any other IDE that has support for Java projects.

- Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

- Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/java/generate-unsplash-image/out/artifacts/generate-unsplash-image_jar/generate-unsplash-image.jar`

```bash
$ cd demos-for-functions/java/generate-unsplash-image/out/artifacts/
$ tar -zcvf code.tar.gz generate-unsplash-image-jar
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case `java -jar generate-unsplash-image.jar`) as your entry point command
- Upload your `tarfile`
- Click 'Activate'

## 🎯 Trigger

- Head over to your function in the Appwrite console and under the Settings Tab, scroll down to variables section and add `UNSPLASH_ACCESS_KEY` with the value of your unsplash `access-key`.
- Go to Overview Tab, click `Execute Now` button and under Custom Data field add your picture search keyword with the format of `{query:"keyword"}`. Then click Execute Now
- See the result at Logs
