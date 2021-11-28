# 📧  updatedAt Function

When a document in any collection is updated, this function will fill updatedAt attribute with the current time, if this rule for updatedAt is configured on a specific collection.

## 📝 Environment Variables

- **APPWRITE_ENDPOINT** - Your Appwrite Project Endpoint ( can be found at `settings` tab on your Appwrite console)
- **APPWRITE_API_KEY** - Your Appwrite Project API Keys ( can be found at `API Keys` tab on your Appwrite console)
- **APPWRITE_FUNCTION_PROJECT_ID** - Your Appwrite Project Id ( can be found at `settings` tab on your Appwrite console)

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/add-updated-at
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
$ cd ..
$ tar -zcvf code.tar.gz add-updated-at
```

* Navigate to Overview Tab of your Cloud Function
* Deploy Tag
* Input the command that will run your function (in this case `ruby main.rb`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 💽 Database

Go to Database tab and follow these steps:

* Open an collection, and open the database collection settings tab
* At the Rules section, click `Add` 
* Fill `updatedAt` as the `Key` & `Label`
* Set the `Rule Type` to text and click `Create`
* Update the settings by clicking `Update` button
* Go to documents tab and try to edit any document
* Click `update` and back to documents tab
* And the `updatedAt` attribute will filled with current date and time

PS: You can add as many rules as wanted, we just need `updatedAt` key to see the cloud function woking

## 🎯 Trigger
Head over to your function in the Appwrite console and under the Settings Tab, enable the `database.documents.update` event.