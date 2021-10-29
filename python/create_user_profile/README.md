## Introduction
This function will be triggered at the new user account creation event. This will create the 'users' collection with the newly registered user's profile information.

## Steps
* Run below code to:
    * Point PIP target to .appwrite directory in the current working directory
    * Install appwrite library from requirements.txt
```
PIP_TARGET=./.appwrite pip3 install -r ./requirements.txt --upgrade --ignore-installed
```
* Run below command to create the tar file
```
tar -zcvf code.tar.gz create_user_profile
```
* Go to appwrite console and generate a new API key. Provide scopes documents.read, documents.write & users.read
* In the appwrite console, create a new function. Choose python runtime
* Go to settings tab of the newly created function and select event "account.create"
* In the same settings tab, add the following environment variables
    * APPWRITE_ENDPOINT ==> If you are running appwrite on localhost, you may want to use ngrok to create a public URL
    * APPWRITE_FUNCTION_PROJECT_ID ==> Your appwrite project id
    * APPWRITE_API_KEY ==> The secret from the new API key generated in first step
* In the Overview tab of the new function, click on "Deploy tag", go to "Manual" tab and provide "python main.py" as Command and upload the tar file.

Now create a new user to trigger the function. You can monitor the function execution in Appwrite console from Functions->Settings->Monitors and see the logs from Functions->Settings->Logs.
