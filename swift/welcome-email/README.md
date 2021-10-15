# 📧 Sending Welcome Emails using Mailgun's Email API
A sample Swift Cloud Function for sending a welcome email to a newly registered user.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **MAILGUN_API_KEY** - API Key for Mailgun 
* **MAILGUN_DOMAIN** - Domain Name from Mailgun

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/swift/welcome-email
```

* Ensure that your project has the following structure
```bash
.
├── Package.swift
├── README.md
└── Sources
    └── WelcomeEmail
        ├── File.swift
        ├── main.swift
        └── Utils.swift

2 directories, 5 files
```

* Build the Swift binary using 

```bash
$ docker run --rm -it -v $(pwd):/app -w /app swift:5.5 swift build
```

* At this point, it you wish to test the code, you can run it using

```sh
docker run \
-e MAILGUN_API_KEY=abcdefg \
-e MAILGUN_DOMAIN=example.com \
-e APPWRITE_FUNCTION_EVENT_DATA="{ \"name\": \"John Doe\", \"email\": \"test@test.com\" }" \
--rm -it -v $(pwd):/app -w /app appwrite/runtime-for-swift:5.5 \
.build/x86_64-unknown-linux-gnu/debug/WelcomeEmail
```

If you get the following message you're good to go! This error should go away once you use a valid `MAILGUN_API_KEY` and `MAILGUN_DOMAIN`.   
```bash
Error: unauthorized
```

* Package your code by creating a tar file 

```bash
tar -zcvf code.tar.gz -C .build/x86_64-unknown-linux-gnu debug/WelcomeEmail
```

* Navigate to the **Overview Tab** of your Cloud Function in the Appwrite Console > Deploy Tag
* Input the command that will run your function (in this case `./WelcomeEmail`) as your entry point command
* Upload your `tarfile` 
* Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and under the Settings Tab, enable the `users.create` and `account.create` event.
