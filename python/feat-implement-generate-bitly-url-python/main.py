import os
import requests
import validators

import json

'''
# Just make sure, within the payload, you have the `url` attribute containing your long url to shorten
# every payload you choose should contain the `url` field

# TODO: 1 > uncomment if you are planning to use from APPWRITE_FUNCTION_DATA
#payload = json.loads(os.environ["APPWRITE_FUNCTION_DATA"])


# TODO: 2 > If using appwrite event, uncomment the below code
#payload = json.loads(os.environ["APPWRITE_FUNCTION_EVENT_DATA"])

'''

# grab the bitly developer account api token from env var
BITLY_TOKEN = os.environ['BITLY_TOKEN']

# grab the long url to shorten

# TODO: 3 > uncomment this line if you are have uncommented any of the above `payload` variables
#long_url = payload["url"]

# TODO: 4 > comment this code out if you are not running once from the AW console
# or if you have uncommented the above `long_url` line
long_url = os.environ['LONG_URL']

def shorten_long_url():
    '''
        shorten a long url using bitly api
    '''

    headers = {
        'Authorization': f'Bearer {BITLY_TOKEN}',
        'Content-Type': 'application/json',
    }

    payload = { 
        "long_url": long_url, 
        "domain": "bit.ly", 
        #"group_guid": "Ba1bc23dE4F" 
    }

    return requests.post('https://api-ssl.bitly.com/v4/shorten', headers=headers, data=json.dumps(payload))

try:
    if long_url:
        if not validators.url(long_url):
            # not a valid url, abort
            raise Exception('long url provided is not a valid url')
            
    response = shorten_long_url()

    if response.status_code == 200 or 201:
        # grab the shortened link
        print(response.json().get('link'))
    
    else:
        # print the error message
        print(response.json().get('description'))

except Exception as e:
    print(str(e))