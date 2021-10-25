# 📧  Generate Open Street Map Preview
<!--  A brief descripption about your Cloud Function  -->
A Ruby Cloud Function for generating map preview using [OpenStreetMap API](https://wiki.openstreetmap.org/wiki/API) and save it into Appwrite Storage.

## 📝 Environment Variables
<!-- Tell the users of your Cloud function, what Environment Variables your function uses. Use the following format -->
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **APPWRITE_ENDPOINT** - <!-- Short Description --> Your Appwrite Endpoint
* **APPWRITE_API_KEY** <!-- Short Description --> Your Appwrite API key with `files.write` permission.

## 🚀 Building and Packaging
<!-- 
Highlight the steps required to build and deploy this cloud function. 

Take a look at this example (https://github.com/appwrite/demos-for-functions/blob/master/nodejs/welcome-email/README.md) for more information.  

Make sure you mention the instructions clearly and also mention the entrypoint command for the function 
-->
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/generate-open-street-map
$ docker run --rm -v $(pwd):/app -w /app --env GEM_HOME=./.appwrite appwrite/env-ruby-2.7:1.0.2 bundle install
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

* Create a tar file

```bash
$ tar -zcvf ../generate-open-street-map.tar.gz .
```
* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `ruby main.rb`) as your entry point command
* Upload your tar file (e.g. `generate-open-street-map.tar.gz`)
* Click 'Activate'

## 🎯 Usage
<!-- Clearly explain the triggers that this cloud function relies on to work correctly. Take a look at the below example: 
Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.
 --> 
* Head over to your function in the Appwrite console
* Click "Execute Now" & provide the input in JSON format:
```json
{
    "latitude": <your-latitude>,
    "longitude": <your-longitude>,
    "zoom-level": <your-zoom-level>
}
```
