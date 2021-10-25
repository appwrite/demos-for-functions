# 📧  Compress Tinypng Image
This function should take link of image as an input, generate compressed version using TinyPNG API and save it into Appwrite Storage.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

Required:
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint.
* **APPWRITE_PROJECT** - Your Appwrite Project ID.
* **APPWRITE_SECRET** - Your Appwrite API key with `document.write` permission.
* **TINIFY_API_KEY** - Your Tinify API Key.
* **IMAGE_URL** - Your image url that want to compress.
Optional:
* **IMAGE_OUTPUT** - Your image name when save on Appwrite Storage. (Default: optimized.png)

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/compress-tinypng-image
$ docker run --rm -v $(pwd):/app -w /app --env GEM_HOME=./.appwrite appwrite/env-ruby-3.0:1.0.0 bundle install
```
* Ensure that your folder structure looks like this
```
.
├── .appwrite/
├── Gemfile
├── Gemfile.lock
├── main.rb
└── README.md
```

* Create a tarfile

```bash
$ tar -zcvf ../compress-tinypng-image.tar.gz .
```

## ⚡ Execution
* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `ruby main.rb`) as your entrypoint command
* Upload your tarfile
* Click 'Activate'

## 🎯 Trigger
Trigger the cloud function using the SDK or HTTP API or the Appwrite Console.

- Sample Input:
Go to Settings tab of your Cloud Function. Add the following environment variables.
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint.
* **APPWRITE_PROJECT** - Your Appwrite Project ID.
* **APPWRITE_SECRET** - Your Appwrite API key with `document.write` permission.
* **TINIFY_API_KEY** - Your Tinify API Key.
* **IMAGE_URL** - Your image url that want to compress.
Optional:
* **IMAGE_OUTPUT** - Your image name when save on Appwrite Storage. (Default: optimized.png)

- Sample Output:
```
File created on storage successfully.
{
	"$id"=>"615f03a7dxxxx", 
	"$permissions"=>{"read"=>[], "write"=>[]}, 
	"name"=>"optimized.png", 
	"dateCreated"=>1633616807, 
	"signature"=>"eaa434eb11885518437562da19a6xxxx", 
	"mimeType"=>"image/png", 
	"sizeOriginal"=>26324
}
```
And your image will save in Appwrite Storage.