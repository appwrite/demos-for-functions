# 😷  Grab daily COVID-19 stats from covid19api.com
This is a demo PHP function to show how to get a daily stats from the covid19api.com API.

## 📝 Environment Variables
Go to the Settings tab of your Cloud Function. Add the following environment variables:

* **COUNTRY_CODE** - (Optional) 2 letter ISO country code.

If no country code is provided, global stats will be displayed. 

## 🚀 Building and Packaging
To package this example as a cloud function, follow these steps:
```bash
$ cd demos-for-functions/php/get-covid-stats

$ tar -zcvf code.tar.gz .
```
* Navigate to the Overview tab of your Cloud Function > Deploy Tag
* Navigate to the Manual tab
* Input the command: `php index.php`
* Upload your tarfile 
* Click 'Create', and 'Activate' after, to activate the tag

## 🎯 Trigger
Go to the Settings tab of your Cloud Function. Select the relevant event to fire this function or schedule it. You can also execute this function manually from the Overview tab.

## 👀 Preview
![cvd1](https://user-images.githubusercontent.com/13732765/136450827-db9899e2-882d-4f25-aad6-a311580fa70d.png)
![cvd2](https://user-images.githubusercontent.com/13732765/136450841-d9155955-e7ea-48b2-8afb-46a0e23c021d.png)
![cvd3](https://user-images.githubusercontent.com/13732765/136450846-e8f17d4d-5558-43b4-b60f-b2c22e0048de.png)