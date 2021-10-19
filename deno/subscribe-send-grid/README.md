# 📧 Subscribe to SendGrid newsletter using email

A sample Deno Cloud Function for subscribing to sendgrid newsletter.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **APPWRITE_API_ENDPOINT** - Appwrite API endpoint
- **APPWRITE_PROJECT_ID** - Appwrite project's ID
- **APPWRITE_API_KEY** - Appwrite project's API key
- **SENDGRID_API_KEY** - Sendgrid's API key

You need to supply the email of user as parameter to the function in this format- { "email": EMAIL_ID }

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

- Create a tarfile

```bash
$ cd demos-for-functions/deno/subscribe-send-grid
$ tar -zcvf code.tar.gz .
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "deno run --allow-net --allow-env index.js") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Overview Tab, click Execute Now and supply the JSON.
