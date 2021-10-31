## Introduction
This functions is created to build the leaderboard of the fitness challenge app available in (here)[https://github.com/gireeshp/fitness_challenge]. Please refer to the app read me for more details about the features and DB collections.

* This function gets triggered when the 'health_last_sync' field is updated in the 'users' collection
* It then sums up activities of all users captured in the "user_activities" collection
* It then loops through all defined challenges and identifies the measurement unit for that challenge (calories or steps), looks for all users participates in that challenge, takes in their scores and calculates the rank
* It finally update the leaderboard with each user rank

## Steps
* Run below code to:
    * Point PIP target to .appwrite directory in the current working directory
    * Install appwrite library from requirements.txt
```
PIP_TARGET=./.appwrite pip3 install -r ./requirements.txt --upgrade --ignore-installed
```
* Run below command to create the tar file
tar -zcvf code.tar.gz calculate_leaderboard
* Go to appwrite console and generate a new API key. Provide scopes collections.read, collections.write, documents.read, documents.write & users.read (the same API key will be used for other functions in this example - hence wider scope)
* In the appwrite console, create a new function. Choose python runtime
* Go to settings tab of the newly created function and select event "account.create"
* In the same settings tab, add the following environment variables
    * APPWRITE_ENDPOINT ==> If you are running appwrite on localhost, you may want to use ngrok to create a public URL
    * APPWRITE_FUNCTION_PROJECT_ID ==> Your appwrite project id
    * APPWRITE_API_KEY ==> The secret from the new API key generated in first step
* In the Overview tab of the new function, click on "Deploy tag", go to "Manual" tab and provide "python main.py" as Command and upload the tar file.
