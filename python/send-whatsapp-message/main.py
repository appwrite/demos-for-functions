import os
from twilio.rest import Client

account_sid = os.environ['ACCOUNT_SID']
auth_token = os.environ['AUTH_TOKEN']
from_number = os.environ['FROM_NUMBER']

data = os.environ.get("APPWRITE_FUNCTION_DATA")

try:
    client = Client(account_sid, auth_token)

    phone_number = data['phoneNumber']
    text = data['text']

    message = client.messages.create(
        body=text,
        from_=f'whatsapp:{from_number}',
        to=f'whatsapp:{phone_number}'
    )

    print(f'WhatsApp message successfully sent to: {phone_number}')

except Exception as e:
    print(str(e))
