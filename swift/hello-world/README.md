# 🚮 Hello World
Swift Hello World cloud functions

## 🚀 Building and Packaging

To package this example as a cloud function, follow these steps. (Please use official swift docker image to build your application.)

```bash
$ cd demos-for-functions/swift/hello-world
$ docker run --rm -v $(pwd):/app -w /app swift:5.5 swift build
```

* Ensure that your folder structure looks like this 
```
├── .build/
├── Sources/
└── Package.swift
```

* Create a tarfile

```bash
$ tar -zcvf code.tar.gz --strip-components=1 -C .build/x86_64-unknown-linux-gnu/ debug/HelloWorld
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `./HelloWorld`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate' to activate your latest deployment
