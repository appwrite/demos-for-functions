# 📡 Get download and upload speed

A sample .NET Cloud Function for get download and upload speed using [SpeedTest.Net](https://www.nuget.org/packages/SpeedTest.Net/).

## 📝 Environment Variables

There are no environment variables!

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/internet-speed

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like this 

```
  GetInternetSpeed -> ......\demos-for-functions\dotnet\internet-speed\bin\Debug\net5.0\linux-x64\GetInternetSpeed.dll
  GetInternetSpeed -> ......\demos-for-functions\dotnet\internet-speed\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Set max delay of function to 180s (it should not take longer)
* Input the command that will run your function (in this case `dotnet GetInternetSpeed.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

You trigger the function by manually executing it in the Appwrite console (Excetute Now) or by using SDK (excetuteFunction).

