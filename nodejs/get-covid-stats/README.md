# 🦠 Function to fetch COVID-19 statistics
Javascript function that prints today's COVID statistics for a country or for the whole world, with a helper function for the format.

## 📝 Environment Variables
When running function, send the country code as function data.

_The country must be in ISO2 format. For example `US`, `HN` etc. If it's not sent, global stats will be returned._

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/nodejs/get-covid-stats
$ npm i
```

* Ensure that your folder structure looks like this 
```
.
├── index.js
├── node_modules/
├── package-lock.json
└── package.json
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz get-covid-stats
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
node index.js
```
## 🎯 Trigger
Can be triggered from manually from the Appwrite Console.