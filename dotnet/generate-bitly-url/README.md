# ⛓ Generate shorter URL from bitly

Function to generate shorter URL address from address via [Bitly](https://bitly.com).

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_FUNCTION_DATA** - URL Addres to generate
* **BITLY_ACCESS_TOKEN** - Bitly bearer token

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/generate-bitly-url

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like this 

```
  GenerateBitlyURL -> ......\demos-for-functions\dotnet\generate-bitly-url\bin\Debug\net5.0\linux-x64\GenerateBitlyURL.dll
  GenerateBitlyURL -> ......\demos-for-functions\dotnet\generate-bitly-url\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dotnet GenerateBitlyURL.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger

You trigger the function by manually executing it in the Appwrite console (Excetute Now) or by using SDK (excetuteFunction).

