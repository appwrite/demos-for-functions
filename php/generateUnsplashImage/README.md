## 📧 Retrieve first Unsplash Search Image result
A sample PHP Cloud Function for retrieving the Url and Author name of the first Unsplash search result from an inputed keyword.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **UNSPLASH_API_KEY** - Your Unsplash Access key 
  
🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/php/generateUnsplashImage
$ composer install
$ tar -zcvf code.tar.gz .
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `php index.php`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'
  
## 🎯 Trigger
After the Building and Packaging step, follow the following steps:

1. Press the Execute Now button.
2. Enter in the Custom Data field the keyword you want to search
3. Press Again Execute Now Button