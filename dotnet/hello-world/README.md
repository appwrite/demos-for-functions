# 🚮 Clean up files in your storage older than XX days
A sample .NET  cloud function to help you create and run Java cloud functions in Appwrite

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/hello-world

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like this 
```
  StorageCleaner -> ......\demos-for-functions\dotnet\hello-world\bin\Debug\net5.0\linux-x64\StorageCleaner.dll
  StorageCleaner -> ......\demos-for-functions\dotnet\hello-world\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dotnet HelloWorld.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## ⏰ Schedule

Head over to your function in the Appwrite console and under the Settings Tab, enter a reasonable schedule time (cron syntax).

For example:

- `*/30 * * * *` every 30 minutes
- `0 * * * *` every hour
- `0 0 * * *` every day
