# 📧 Welcome Email 
A sample Ruby Cloud Function for sending a welcome email to a newly registered user.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **MAILGUN_API_KEY** -  API Key for Mailgun
* **MAILGUN_DOMAIN** - Domain Name from Mailgun

## 🚀 Building and Packaging
Make sure to install requirements by:
```ruby
gem install json
```

* Ensure that your folder structure looks like this 
```
.
├── .appwrite/
├── main.rb
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz welcome-email
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `ruby main.rb`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## Screenshot of working

![exec](https://user-images.githubusercontent.com/73291466/136646754-70c344ad-8bee-4f2b-ab89-2ab80a4e8864.png)


## 🎯 Trigger

When any user is created on the app, this function is automatically triggered. Allow events `account.create` and `users.create` from cloud function settings
