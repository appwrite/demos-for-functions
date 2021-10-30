# 📧 Add an email to SendGrid list

A sample Ruby Cloud Function for adding the given email address to a Sendgrid Newsletter.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_ENDPOINT** - Your Appwrite Endpoint
- **APPWRITE_PROJECT_ID** - Appwrite project's ID
- **APPWRITE_API_KEY** - Your Appwrite Project's API key
- **SENDGRID_API_KEY** - Your Sendgrid API key
- **SENDGRID_LIST_ID** - List ID of the list the email should be added to.

You need to supply the email of user as parameter to the function in this format- { "email": EMAIL_ID }

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/subscribe-to-sendgrid
$ docker run --rm -v $(pwd):/app -w /app --env GEM_HOME=./.appwrite appwrite/env-ruby-3.0:1.0.0 bundle install
```

- Ensure that your folder structure looks like this

```
.
├── Gemfile
├── main.rb
└── README.md
```

- Create a tarfile

```bash
$ tar -zcvf ../subscribe-to-sendgrid.tar.gz .
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case `ruby main.rb`) as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Overview Tab, click `"Execute Now"` and supply the required JSON.
