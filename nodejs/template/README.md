# Template
This template is meant to allow developers to quickly get started with Appwrite's Cloud Functions. Just follow along 

* Clone the repo
```bash
$ git clone https://github.com/appwrite/demos-for-functions
```

* Navigate to the templates folder for the language you require
```bash
$ cd demos-for-functions/nodejs/template
```

* Install your dependencies using 
```bash
$ npm install --save <package_name>
```

* Write you code and create a tarfile
```bash
$ cd ..
$ tar -zcvf code.tar.gz template
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command
```bash
node index.js
```
