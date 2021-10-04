import os
import requests
import validators
import sys

import json

# grab the bitly developer account api token from env var
BITLY_TOKEN = os.environ.get('BITLY_TOKEN')

if BITLY_TOKEN is None:
    print('please provide your Bitly Account token!')
    sys.exit()


def get_long_url() -> str:
    '''
        attempt to find provided long url from 
        different env vars possible
    '''

    from_func_data  = os.environ.get("APPWRITE_FUNCTION_DATA")
    from_event_data = os.environ.get("APPWRITE_FUNCTION_EVENT_DATA")
    from_global_env = os.environ.get('LONG_URL')

    if from_func_data:
        if len(from_func_data) > 0:
            # assume, there is a valid long url to shorten
            return from_func_data

    elif from_event_data:
        if len(from_event_data) > 0:
            return from_event_data

    else:
        if len(from_global_env) > 0:
            return from_global_env

    return ''

# save long url got from env vars if available
long_url = get_long_url()


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
            raise Exception(f'long url: {long_url}, provided is not a valid url')
            
    response = shorten_long_url()

    if response.status_code == 200 or 201:
        # grab the shortened link
        print(response.json().get('link'))
    
    else:
        # print the error message
        print(response.json().get('description'))

except Exception as e:
    print(str(e))