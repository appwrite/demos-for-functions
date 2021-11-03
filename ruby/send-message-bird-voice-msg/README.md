# 📧  Send MessageBird Voice Message

A sample Ruby Cloud Function for sending a voice message using MessageBird. 


## 📝 Environment Variables
<!-- Tell the users of your Cloud function, what Environment Variables your function uses. Use the following format -->

* **MESSAGE_BIRD_ACCESS_KEY** - API Key for MessageBird

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/send-message-bird-voice-msg
$ docker run --rm -v $(pwd):/app -w /app --env GEM_HOME=./.appwrite appwrite/env-ruby-3.0:1.0.0 bundle install
```

- Ensure that your folder structure looks like this

```
.
├── .appwrite/
├── Gemfile
├── Gemfile.lock
├── main.rb
└── README.md
```

- Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz send-message-bird-voice-msg
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "ruby main.rb") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Head over to your function in the Appwrite console and click on execute. Provide `phoneNumber` and `text` in JSON format and continue.

For example:

- `{"text": "This is the message body", "phoneNumber": "xxx-xxx-xxx"}`