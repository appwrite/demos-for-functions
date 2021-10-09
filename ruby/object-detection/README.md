# 📷 Object Detection using Cloudmersive Vision API
A sample Ruby Cloud Function for object detection on an image file uploaded by the user. 

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

Required:
* **APPWRITE_API_KEY** - Create a key from the Appwrite console with the following scope (`files.read`)
* **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
* **CLOUDMERSIVE_API_KEY** - API Key for Cloudmersive

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/object-detection
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
$ tar -zcvf ../object-detection.tar.gz .
```

## ⚡ Execution
* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `ruby main.rb`) as your entrypoint command
* Upload your tarfile
* Click 'Activate'

* Manually run:
- `cd demos-for-functions/ruby/object-detection`
- `cp .env.example .env`
- Change your config in .env
- `bundle install`
- `ruby main.rb`

## 🎯 Trigger
Trigger the cloud function using the SDK or HTTP API or the Appwrite Console.

Input:
```
{
	"$id": "your_file_id"
}
```

Output:
```
{
	:Successful=>true, 
	:Objects=>
		[
			{
				:ObjectClassName=>"dog", 
				:Height=>228, 
				:Width=>266, 
				:Score=>0.5911540985107422, 
				:X=>11, 
				:Y=>19
			}
		], 
	:ObjectCount=>1
}
```