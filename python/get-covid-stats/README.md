# 🦠 Function for fetching COVID-19 statistics
A simple python function that prints today's COVID statistics for a country or for the whole world.
## 📝 Environment Variables
When running function, pass country code as function data.

* **APPWRITE_FUNCTION_DATA** - Code of the country in the ISO2 format to get the statistic for. For example `US`, `DE` etc. If unset, global stats will be returned.

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/get-covid-stats

$ PIP_TARGET=./.appwrite pip install -r ./requirements.txt --upgrade --ignore-installed 
```

* Ensure that your folder structure looks like this 
```
.
├── .appwrite/
├── main.py
└── requirements.txt
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz get-covid-stats
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
python main.py
```
## 🎯 Trigger
Can be triggered from manually from the Appwrite Console.
