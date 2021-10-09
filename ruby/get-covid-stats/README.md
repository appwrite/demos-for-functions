# 😷 Get Covid Statistic

A sample Ruby Cloud function that fetch and displays today's coronavirus stats for a given country or for the globe.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/ruby/get-covid-stats
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
$ tar -zcvf code.tar.gz get-covid-stats
```

- Navigate to the Overview Tab of your Cloud Function > Deploy Tag
- Input the command that will run your function (in this case "ruby main.rb") as your entrypoint command
- Upload your tarfile
- Click 'Activate'

## 🎯 Trigger

Trigger the cloud function using the SDK or HTTP API or the Appwrite Console.

- Sample Input:

```Json
{
    "COUNTRY_CODE": "IN"
}
```

**COUNTRY_CODE** - The country code for which COVID data will be fetched. If not provided, global data will be fetched.

Sample response:

```ruby
Covid stats of India today:
New cases: 18833
New deaths: 278
New recovery: 0
```