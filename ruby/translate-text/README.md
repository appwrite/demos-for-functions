# 🌐 Translate text from one language to another

A sample Ruby Cloud Function for translating text from one language to another using google translate.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/translate-text
```

Add this line to your application's Gemfile:

```bash
gem 'free_google_translate'
```

And then execute: 
```bash
$ bundle
```

Or install it yourself as:

```bash
$ gem install free_google_translate
```

- Ensure that your folder structure looks like this

```
.
├── main.rb
├── Gemfile
├── Gemfile.lock
├── README.md
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
