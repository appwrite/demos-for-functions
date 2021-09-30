import os
import requests
import json

# parsing payload
payload = json.loads(os.environ['APPWRITE_FUNCTION_EVENT_DATA'])
name = payload['name']
email = payload['email']
# see: https://appwrite.io/docs/models/user

def send_simple_message():
    '''using mailgun API to send email'''

    return requests.post(
        f"https://api.mailgun.net/v3/{os.environ['MAILGUN_DOMAIN']}/messages",
            auth=("api", f"{os.environ['MAILGUN_API_KEY']}"),
            data={"from": f"Excited User <mailgun@{os.environ['MAILGUN_DOMAIN']}>",
                "to": [ email ],
                "subject": f"Hello, {name}",
                "text": "Testing some Mailgun awesomness!"})

try:
    response = send_simple_message()
    print("response code: ", response.status_code)
except Exception as e:
    print(e)
