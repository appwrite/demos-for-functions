# 📧 Generate Giphy Gif

A sample .NET Cloud Function for finding a gif using Giphy search API.

## ☁️ Create a New Cloud Function

Navigate to 'Functions' and 'Add Function.'
Use '.NET 5.0' environment.

## 📝 Environment Variables

<!-- Tell the users of your Cloud function, what Environment Variables your function uses. Use the following format -->

- **GIPHY_API_KEY** - Giphy API key used for calling the search API.
- **APPWRITE_FUNCTION_DATA** - Keyword that is used for searching.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/generate-giphy-gif

$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

- Ensure that your output looks like this

```
  GenerateGiphyGif -> ......\demos-for-functions\dotnet\generate-giphy-gif\bin\Debug\net5.0\linux-x64\GenerateGiphyGif.dll
  GenerateGiphyGif -> ......\demos-for-functions\dotnet\generate-giphy-gif\bin\Debug\net5.0\linux-x64\publish\
```

- Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case `dotnet GenerateGiphyGif.dll`) as your entrypoint command
- Upload your tarfile
- Click 'Activate'
  -->

## 🎯 Trigger

Head over to your function in the Appwrite console and press **Execute**. Enter a search keyword in the Custom Data textbox. Output Logs should contain the URL of the found gif. In case there were any errors they will be displayed in the Error Logs.
