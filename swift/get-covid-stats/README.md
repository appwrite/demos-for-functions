# 🦠 Get COVID-19 stats from an API
A sample Swift Cloud Function for fetching data from a REST API, in this case [Covid19Api](https://covid19api.com/).

## 📝 Environment Variables
When running function, pass country code or name as function data.

* **APPWRITE_FUNCTION_DATA** - Code of the country in the ISO2 format, or country name you want the statistics for. Comparison is case-insensitive. For example `US`, `De`, `india` etc. If unset, global stats will be returned.

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/swift/get-covid-stats
docker run --rm -it -v $(pwd):/app -w /app swift:5.5 swift build -c release
```

* Ensure that your folder structure looks like this
```
├── .build/x86_64-unknown-linux-gnu/release/GetCovidStats
├── Sources/
└── Package.swift
```

* Create a tarfile

```bash
$ tar -zcvf code.tar.gz -C .build/x86_64-unknown-linux-gnu/ release/GetCovidStats
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `./GetCovidStats`) as your entrypoint command
* Upload your tarfile
* Click 'Activate'

## 🎯 Trigger
You can trigger this function using the "Execute now" button in Appwrite Console or using SDK's `createExecution` function.
