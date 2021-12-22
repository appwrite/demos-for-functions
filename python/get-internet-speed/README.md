## 📧 Function for getting internet speed
A sample Python Cloud Function to check the download and upload speed (and related statistics) of the server.

## 📝 Environment Variables
No environment variables needed.
  
🚀 Building and Packaging
To package this example as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/get-internet-speed

$ PIP_TARGET=./.appwrite pip install -r ./requirements.txt --upgrade --ignore-installed 
```

* Ensure that your folder structure looks like this 
```
.
├── .appwrite/
├── main.py
├── README.md
└── requirements.txt
```

* Create a tarfile

```bash
$ cd ..
$ tar -zcvf code.tar.gz get-internet-speed
```

* Upload the tarfile to your Appwrite Console and use the following entrypoint command

```bash
python main.py
```

* Click 'Activate'
* Increase the timeout for the function to at least 120 sec or more (in Function setting > Timeout field).
* Depending on the location and the server's speed, you will need to adjust the timeout accordingly.

## 🎯 Trigger
After the Building and Packaging step, follow the following steps:

1. Press the Execute Now button.
2. Wait for the function to finish
3. Check the Logs for the result
Example output: `{'download_speed': '45.07 Mbps', 'upload_speed': '61.02 Mbps', 'ping': '7.274 ms'}`

