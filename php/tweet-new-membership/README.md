# 📧 Posting a tweet when a new member joins the team

A sample PHP Cloud Function that posts a tweet when a new member is created (teams.membership.create event triggered).

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

### Appwrite configurations

- **APPWRITE_API_ENDPOINT** - API endpoint of your Appwrite project
- **APPWRITE_PROJECT_ID** - Your Appwrite project's unique ID
- **APPWRITE_API_KEY_SECRET** - Your Appwrite project's secret key

Go to https://developer.twitter.com/ and register for developer account. Create a project (give read and write permissions) and get the following keys/secrets/tokens-

### Twitter configurations

- **TWITTER_CONSUMER_KEY** - Consumer key provided by Twitter
- **TWITTER_CONSUMER_SECRET** - Consumer secret corresponding to above key
- **TWITTER_ACCESS_TOKEN** - Access token provided by Twitter (found under Authentication Tokens)
- **TWITTER_ACCESS_TOKEN_SECRET** - Access secret corresponding to above token

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/php/tweet-new-membership

$ composer install
```

- Ensure that your folder structure looks like this

```
.
├── index.php
├── vendor/
├── composer.lock
└── composer.json
└── README.md
```

- Follow instructions on https://appwrite.io/docs/functions to run the functions.
- You can follow the following manual approach (taken from above link).

```bash
$ cd ..

$ tar -zcvf code.tar.gz tweet-new-membership
```

- Open settings of your Cloud Function > Deploy Tag
- Select Manual from the tabs shown on top of the dialog box
- Input the command that will run your function (in this case "php index.php") as your entrypoint command
- Upload your tarfile and click on create.
- 'Activate' the newly created tag.

## 🎯 Trigger

This function is triggered by the event teams.membership.create. To simulate this event, go to Users on Appwrite Dashboard. From Teams tab, create a new team and add a new member to it. This will trigger the function. Go the logs of the function to view the output.

## Output

STDOUT is the response returned by Twitter which contains information about the tweet posted.
