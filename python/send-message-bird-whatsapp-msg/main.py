import os
import json
import requests

from appwrite.client import Client

# MessageBird Whatsapp API
MESSAGE_BIRD_API_URL = 'https://conversations.messagebird.com/v1/send'

# Enviornment variables
MESSAGE_BIRD_API_KEY = os.environ['MESSAGE_BIRD_API_KEY']
WHATSAPP_CHANNEL_ID = os.environ['WHATSAPP_CHANNEL_ID']
REPORT_URL = os.environ['REPORT_URL']

# Function Payload
payload = json.loads(os.environ['APPWRITE_FUNCTION_DATA'])

PHONE_NUMBER = payload['PHONE_NUMBER']
TEXT = payload['TEXT']

# Intialize appwrite SDK
client = Client()
(
    client
    .set_endpoint(os.environ['APPWRITE_ENDPOINT'])
    .set_project(os.environ['APPWRITE_FUNCTION_PROJECT_ID'])
    .set_key(os.environ['APPWRITE_API_KEY'])
)


# Sending a simple message via messagebird whatsapp api
def send_whatsapp_message():
    """function to send message through messagebird api"""
    data = {
        'to': PHONE_NUMBER,
        'from': WHATSAPP_CHANNEL_ID,
        'type': 'text',
        'content': {
            'text': TEXT,
            'disableUrlPreview': False
        },
        'reportUrl': REPORT_URL
    }

    return requests.post(
        MESSAGE_BIRD_API_URL,
        auth=('Authorization', MESSAGE_BIRD_API_KEY),
        json=data)


try:
    response = send_whatsapp_message()
    print(response)
except Exception as e:
    print(e)
