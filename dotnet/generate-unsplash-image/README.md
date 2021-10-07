# Get Image Url and Image Author from Unsplash API
A sample .NET cloud function to retrieve an Image Url and Author based on a keyword

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **UNSPLASH_KEYWORD** - The keyword you want to search
* **UNSPLASH_ACCESS_KEY** - The Access Key for your Unsplash Application

## 📝 Creating an Unsplash Application
First, you have to setup a Developer Account on this link:
https://unsplash.com/join

Once you're logged in, go to https://unsplash.com/oauth/applications and click on New Application.

After creating your application, copy your Access Key and Secret Key to a safe place.

For more information, please, go to the Unsplash Documentation
https://unsplash.com/documentation#creating-a-developer-account

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/dotnet/generate-unsplash-image
$ dotnet publish --runtime linux-x64 --framework net5.0 --no-self-contained
```

* Ensure that your output looks like this 
```
  GenerateUnsplashImage -> ......\demos-for-functions\dotnet\generate-unsplash-image\bin\Debug\net5.0\linux-x64\HelloWorld.dll
  GenerateUnsplashImage -> ......\demos-for-functions\dotnet\generate-unsplash-image\bin\Debug\net5.0\linux-x64\publish\
```

* Create a tarfile

```bash
$ tar -C bin/Debug/net5.0/linux-x64 -zcvf code.tar.gz publish
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `dotnet GenerateUnsplashImage.dll`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'