# 📧 Posting a tweet when a new member joins the team

A sample Kotlin Cloud Function that posts a tweet when a new member is created i.e. `teams.membership.create` event is triggered.

## 📝 Environment Variables

Go to https://developer.twitter.com/ and register for developer account. Create a project (give read and write permissions) and get the following keys/secrets/tokens

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **TWITTER_API_KEY** - Api key provided by Twitter
- **TWITTER_API_KEY_SECRET** - Api Key secret corresponding to above key
- **TWITTER_ACCESS_TOKEN** - Access token provided by Twitter (found under Authentication Tokens)
- **TWITTER_ACCESS_TOKEN_SECRET** - Access secret corresponding to above token

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

* Import the project into IntelliJ, or any other IDE that has support for Kotlin projects.

* Build a jar for the project. Follow the instructions [here](https://hardiksachan.hashnode.dev/build-a-jar-with-gradle)

* Create a tarfile

```bash
$ cd demos-for-functions/kotlin/tweet-new-membership/build/libs/
$ tar -zcvf code.tar.gz ./tweet-new-membership-1.0-SNAPSHOT.jar
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `java -jar tweet-new-membership-1.0-SNAPSHOT.jar`) as your entry point command
* Upload your `tarfile`
* Click 'Activate'

## 🎯 Trigger

This function is triggered by the event `teams.membership.create`. To simulate this event, go to Users on Appwrite Dashboard. From Teams tab, create a new team and add a new member to it. This will trigger the function. Go the logs of the function to view the output.
### Note:
If you don't want to set up an SMTP server, you can create a new membership by using Server Side SDK. But you do need to set `_APP_SMTP_HOST` environment variable.
You can read more about it [here](https://appwrite.io/docs/environment-variables)

## Output

STDOUT is the `id` of tweet returned by Twitter