# 🌐 Get Internet Speed

A sample Ruby Cloud function to check your network's download and upload speed. 

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/get-internet-speed
```

Add this line to your application's Gemfile:

```bash
gem 'speedtest'
```

And then execute: 
```bash
$ bundle
```

Or install it yourself as:

```bash
$ gem install speedtest
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
$ tar -zcvf code.tar.gz get-internet-speed
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "ruby main.rb") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Trigger the cloud function using the SDK or HTTP API or the Appwrite Console.
