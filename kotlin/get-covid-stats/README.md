# 🦠 Get Covid-19 stats from a third party API
A sample Kotlin Cloud Function for fetching data from a third party api, in this case [Covid19Api](https://covid19api.com/).

## 📝 Environment Variables
When running function, pass country code or name as function data.

* **APPWRITE_FUNCTION_DATA** - Code of the country in the ISO2 format, or country name to get the statistics for. For example `US`, `DE`, `India` etc. If unset, global stats will be returned.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, or any other IDE that has support for Kotlin projects. 

* Build a jar for the project. If you are using Intellij, follow these steps:
    - Open gradle tab, goto `Tasks/build/jar` and create a jar file. <img src="/screenshots/gradle-build-instr.png" width="512">

* Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/kotlin/get-covid-stats/build/libs/get-covid-stats-1.0-SNAPSHOT.jar`
<img src="/screenshots/gradle-jar-loc.png" width="512">

```bash
$ cd demos-for-functions/kotlin/get-covid-stats/build/libs/
$ tar -zcvf code.tar.gz get-covid-stats-1.0-SNAPSHOT.jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar get-covid-stats-1.0-SNAPSHOT.jar`) as your entry point command
* Upload your `tarfile` 
* Click 'Activate'

## 🎯 Trigger
Can be triggered from manually from the Appwrite Console.