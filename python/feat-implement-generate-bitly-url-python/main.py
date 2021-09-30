import os
import requests
import validators

from json import dumps

BITLY_TOKEN = os.environ['BITLY_TOKEN']
LONG_URL = os.environ['LONG_URL']

def shorten_long_url():
    '''
        shorten a long url using bitly api
    '''

    headers = {
        'Authorization': f'Bearer {BITLY_TOKEN}',
        'Content-Type': 'application/json',
    }

    payload = { 
        "long_url": LONG_URL, 
        "domain": "bit.ly", 
        #"group_guid": "Ba1bc23dE4F" 
    }

    return requests.post('https://api-ssl.bitly.com/v4/shorten', headers=headers, data=dumps(payload))

try:
    if LONG_URL:
        if not validators.url(LONG_URL):
            # not a valid url, abort
            raise Exception('long url provided is not a valid url')
            
    response = shorten_long_url()

    if response.status_code == 200 or 201:
        print(response.json().get('link'))
    
    else:
        print(response.json().get('description'))

except Exception as e:
    print(str(e))