# Implement getCovidStats() Appwrite Function ⚡
A sample Java Cloud Function for fetching COVID19 stats for the day from [COVID19 API](https://covid19api.com/)

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **COUNTRY_CODE** - Country Code to fetch data for. (Empty COUNTRY_CODE fetches Global Data)

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, Eclipse or any other IDE that has support for Java projects.


* Build a jar for the project. Here's a [Stack Overflow answer](https://stackoverflow.com/questions/1082580/how-to-build-jars-from-intellij-properly) to help you.

* Create a tarfile

If you followed the steps correctly, our output jar file would mostly be created at `demos-for-functions/java/getCovidStats/out/artifacts/getCovidStats_jar/getCovidStats.jar`

```bash
$ cd demos-for-functions/java/getCovidStats/out/artifacts/
$ tar -zcvf code.tar.gz getCovidStats_jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar getCovidStats.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate' and Execute the function.
