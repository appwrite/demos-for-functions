# 😷 Generate Unsplash Image

A sample Ruby Cloud function to generate a picture URL from Unsplash's API.

## 📝 Environment Variables

Go to Settings tab of your Cloud Function. Add the following environment variables.

- **UNSPLASH_ACCESS_KEY** - Your Unsplash Access Key for authorization

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/generate-unsplash-image
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
$ tar -zcvf code.tar.gz generate-unsplash-image
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "ruby main.rb") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

- Head over to your function in the Appwrite console and under the Settings Tab, scroll down to variables section and add `UNSPLASH_ACCESS_KEY` with the value of your unsplash `access-key`.
- Go to Overview Tab, click `Execute Now` button and under Custom Data field add your picture search keyword with the format of `{query: "keyword"}`. Then click Execute Now
- See the result at Logs
