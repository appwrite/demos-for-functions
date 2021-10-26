# 📷 Object Detection using Cloudmersive Vision API

A sample Kotlin Cloud Function for object detection on an image file uploaded by the user.

## 📝 Environment Variables

Add the following environment variables in your Cloud Functions settings.

- **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.read`)
- **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
- **CLOUDMERSIVE_API_KEY** - API Key for Cloudmersive

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

- Import the project into IntelliJ, Eclipse or any other IDE that has support for Kotlin projects.

- Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

- Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/kotlin/object-detection/out/artifacts/object_detection_jar/object-detection.jar`

```bash
$ cd kotlin/object-detection/out/artifacts
$ tar -zcvf code.tar.gz object_detection_jar
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case `java -jar object-detection.jar`) as your entry point command
- Upload your `tarfile`
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `storage.files.create` event.
