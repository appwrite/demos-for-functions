# Template
This template is meant to allow developers to quickly get started with Appwrite's Cloud Functions. Just follow along 

* Clone the repo
```bash
$ git clone https://github.com/appwrite/demos-for-functions
```

* Navigate to the templates folder for the language you require

```bash
$ cd demos-for-functions/python/template
```

* Add your dependecies to `requirements.txt` and run the following command

```bash
$ PIP_TARGET=./.appwrite pip install -r ./requirements.txt --upgrade --ignore-installed
```

* Write you code. When you run the code locally, ensure that your libraries are added to the `PYTHONPATH`
```bash
PYTHONPATH=./.appwrite python main.py
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz template
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
python main.py
```
