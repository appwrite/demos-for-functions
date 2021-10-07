# Check COVID today's status
A sample .NET cloud function to give you COVID stats in Appwrite

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **COUNTRY_SLUG** - The country slug which COVID data you want to get. If not informed, global data will be fetch.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/get-covid-stats

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like this 
```
  GetCovidStats -> ......\demos-for-functions\dotnet\get-covid-stats\bin\Debug\net5.0\linux-x64\HelloWorld.dll
  GetCovidStats -> ......\demos-for-functions\dotnet\get-covid-stats\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dotnet GetCovidStats.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'
