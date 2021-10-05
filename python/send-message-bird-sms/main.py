import messagebird
import json
import os

client = messagebird.Client(os.environ['MESSAGE_BIRD_KEY'])

# parsing payload
payload = json.loads(os.environ['APPWRITE_FUNCTION_DATA'])
phoneNumber = payload['phoneNumber']
text = payload['text']

try:
    message = client.message_create(
        os.environ['ORIGINATOR'],
        phoneNumber,
        text
    )
    print(message.recipients.items[0].status)
except Exception as e:
    print(e)
