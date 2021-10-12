# 🌐 Get Internet Speed

A sample Ruby Cloud function to check your network's download and upload speed. 

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/get-internet-speed
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
$ tar -zcvf code.tar.gz get-internet-speed
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "ruby main.rb") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Trigger the cloud function using the SDK or HTTP API or the Appwrite Console.

- Sample Output:

```ruby
Download speed is: 9.51 Mbps
Upload speed is: 3.84 Mbps
```
