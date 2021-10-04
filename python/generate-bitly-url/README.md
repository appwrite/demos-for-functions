# 🔗 Shorten Long Link
A Python Cloud Function for generating a bitly short link from a passed long url link.

<br>
<br>

## 🚀 Result
Here is our very own shortened appwrite generated link
![result](docs-images/result.png)
<br>

<br>
<br>

## 📝 Environment Variables
Go to Settings tab of your Cloud Function. Add the following environment variables.

* **BITLY_TOKEN** -  API Key for Bitly

If you are testing from AW console, provide this environment variable
* **LONG_URL** - example long url to shorten
<br>
![bitly.png](docs-images/console.png)
<br>
<br>

You can use both the `APPWRITE_FUNCTION_DATA` or `APPWRITE_FUNCTION_EVENT_DATA` default environment variables too depending on your use-case.<br>
In each scenario, provide the `url` you want to shorten as a string
<br><br>
Consider the scenarios (more info [on Appwrite docs](https://appwrite.io/docs/functions#enviromentVariables))
<br>
<br>

## Scenario 1: `APPWRITE_FUNCTION_DATA`
This variable can be set only when triggering a function using the SDK or HTTP API and the Appwrite Dashboard. <br>

<br>
If using this from appwrite console, paste this example code on the popup<br>

```
 https://appwrite.io/

```
<br>
<br>

## Scenario 2: `APPWRITE_FUNCTION_EVENT_DATA`
Your function event payload. This value is available only when your function trigger is 'event'. This variable value contains a string in JSON format with your specific event data.<br>
<br>
<br>



## Bitly Account
If you do not have an account already, [registere here](https://bitly.com/pages/pricing/v2) for free.
<br>
Once registered, login and go on your dashboard, from there click your account name on the top right corner and go to `settings`
<br>
Under `settings`, scroll to `Developer settings` and click on `API`
<br>
Under this tab, notice the `Access token` menu, enter your account password you used to login / register and client `Generate token`
<br>
After a success, copy the token and paste on your AW console variables as `BITLY_TOKEN`
<br>
![bitly.png](docs-images/bitly.png)
<br>
<br>

## 🚀 Building and Packaging

To package this as a cloud function, follow these steps.

```bash
$ cd demos-for-functions/python/generate-bitly-url-python

$ PIP_TARGET=./.appwrite pip install -r ./requirements.txt --upgrade --ignore-installed 
```

* or cd into your current directory and run
```bash
$ pip install -t ./.appwrite -r ./requirements.txt --upgrade --ignore-installed
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
$ tar -zcvf code.tar.gz generate-bitly-url-python
```

* Navigate to the Overview Tab of your Cloud Function > Deploy Tag
* Input the command that will run your function (in this case `python main.py`) as your entrypoint command
* Upload your tarfile 
* Click 'Activate'

## 🎯 Trigger
You can use any event here to enable automatic configuring of this cloud function, just make sure that the payload contains the `url` string value so that the function will pick it up as the field containing the long url to shorten


