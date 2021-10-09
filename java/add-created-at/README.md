# Add Created At

A sample Java Cloud Function to add `createdAt` value to the document if the rule is specified in the collection.


## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_ENDPOINT** - Your Appwrite Project Endpoint ( can be found at `settings` tab on your Appwrite console)
- **APPWRITE_FUNCTION_PROJECT_ID** - Your Appwrite Project Id ( can be found at `settings` tab on your Appwrite console)
- **APPWRITE_API_KEY** - Your Appwrite Project API Keys ( can be found at `API Keys` tab on your Appwrite console)

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

- Import the project into IntelliJ, Eclipse or any other IDE that has support for Java projects.

- Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

- make sure the `META-INF/MANIFEST.MF` output directory was set to `[your-path]/main/resources` not `[yourpath]/main/java   `

- Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/java/generate-unsplash-image/out/artifacts/generate-unsplash-image_jar/generate-unsplash-image.jar`

```bash
$ cd demos-for-functions/java/add-created-at/out/artifacts/
$ tar -zcvf code.tar.gz add_created_at_jar
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case `java -jar add-created-at.jar`) as your entry point command
- Upload your `tarfile`
- Click 'Activate'

## 💽 Database

Go to Database tab and follow these steps:

- Add new Collection, then database collection settings will popup
- At the Rules section, click add
- fill `createdAt` as the key & label
- set the Rule Type to text and click create
- update the settings by clicking update button
- Go to Documents tab and click add document
- fill in the data needed and click create
- there should be `Document ID` and `Collection ID` on the right side (we need both of this as data when triggering cloud functions )

PS: You can add as many rules as wanted, we just need `createdAt` key to see the cloud functions working


## 🎯 Trigger

- Head over to your function in the Appwrite console, then add new function with Java Runtimes
- under created functions Settings Tab, scroll down to variables section and add all the Environment Variables above.
- Go to Overview Tab, click `Execute Now` button and under Custom Data field add your `Document ID` and `Collection ID` for example `{ collectionId: "6161a0bf7a383", documentId: "6161a0eddf407" }`.
- Then click Execute Now
- See the result at Logs

## 📓 Note

- if `APPWRITE_ENDPOINT` is localhost, error will occur `Failure(java.net.ConnectException: Failed to connect to localhost/127.0.0.1:80)`, to test it locally try to use services like ngrok which provide tunneling to localhost. 