# Kotlin Hello World Cloud Function
A sample Kotlin cloud function to help you create and run Kotlin cloud functions in Appwrite


## 🚀 Building and Packaging

We provide a OpenJDK JRE based docker container as a Java/Kotlin Functions runtime environment with Appwrite. So before you can deploy your Kotlin functions to Appwrite, you need to compile it to JVM code. For deploying `HelloWorld` to Appwrite firs compile using below commands.

```bash
$ cd demos-for-functions/kotlin/hello_world

$ kotlinc HelloWorld.kt -include-runtime -d HelloWorld.jar

```
This will create a `HelloWorld.jar` file inside `hello_world` folder.

* Ensure that your folder structure looks like this 
```
.
├── HelloWorld.java
├── HelloWorld.jar
```

* Deploy using CLI
```
appwrite functions createTag \
    --functionId=<id> \
    --command='java -jar HelloWorld.jar' \
    --code='.'
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Click 'Activate' to activate your latest deployment
