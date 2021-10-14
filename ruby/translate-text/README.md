# 🌐 Translate text from one language to another

A sample Ruby Cloud Function for translating text from one language to another using google translate.

## 📝 Environment Variables

No environment variables needed.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/translate-text
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
$ tar -zcvf code.tar.gz translate-text
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "ruby main.rb") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Trigger the cloud function using the SDK or HTTP API or the Appwrite Console.

You need to include the following data to properly trigger the function

```Json
{
    "text": "hello",
    "sourceLanguage": "en",
    "destinationLanguage": "es"
}
```

Example response:

```text
Hola
```