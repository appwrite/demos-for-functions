# Template
This template is meant to allow developers to quickly get started with Appwrite's Cloud Functions. Just follow along 

* Clone the repo
```bash
$ git clone https://github.com/appwrite/demos-for-functions
```

* Navigate to the templates folder for the language you require

```bash
$ cd demos-for-functions/deno/template
```

* Write you code and create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz template
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
deno run --allow-net index.js
```
