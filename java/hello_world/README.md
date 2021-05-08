# Java Hello World Cloud Function
A sample Java cloud function to help you create and run Java cloud functions in Appwrite


## 🚀 Building and Packaging

We provide a OpenJDK JRE based docker container as a Java/Kotlin Functions runtime environment with Appwrite. So before you can deploy your Java functions to Appwrite, you need to compile it to JVM code. For deploying `HelloWorld` to Appwrite firs compile using below commands.

```bash
$ cd demos-for-functions/java/hello_world

$ javac ./HelloWorld.java

```
This will create a `HelloWorld.class` file inside `hello_world` folder.

* Ensure that your folder structure looks like this 
```
.
├── HelloWorld.java
├── HelloWorld.class
```

* Deploy using CLI
```
appwrite functions createTag \
    --functionId=<id> \
    --command='java HelloWorld' \
    --code='.'
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Click 'Activate' to activate your latest deployment
