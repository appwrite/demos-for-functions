# 📧  Shorten URL using Bitly API
This function takes an url as an input, generate a short URL using Bitly and output the shorten URL.

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

Required:
* **APPWRITE_API_ENDPOINT** — Your Appwrite Endpoint
* **APPWRITE_SECRET_KEY** — Your Appwrite API key
* **BITLY_ACCESS_TOKEN** — Bitly access token (you should generate it [here](https://app.bitly.com/settings/api/))

Optional (available with an Enterprise plan subscription):
* **BITLY_CUSTOM_DOMAIN** — custom domain for short links (default `bit.ly`)
* **BITLY_GROUP_GUID** — group id

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/generate-bitly-url
$ docker run --rm -v $(pwd):/app -w /app --env GEM_HOME=./.appwrite appwrite/env-ruby-3.0:1.0.0 bundle install
$ tar -zcvf ../code.tar.gz .
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
$ tar -zcvf ../code.tar.gz .
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `ruby main.rb`) as your entrypoint command
* Upload your tarfile
* Click 'Activate'

## Usage
* On the Overview Tab click Execute Now
* Insert original url you want to shorten to Custom Data field and submit

<img src="http://g.recordit.co/CufD3I9wL8.gif" width="643" height="448">
