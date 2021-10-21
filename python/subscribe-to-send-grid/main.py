#Importing the required libraries
import os
import json
import requests
from appwrite.client import Client

#Initialise the Appwrite Client SDK for Python
client = Client()

(client
    .set_endpoint(os.environ.get('APPWRITE_ENDPOINT')) #Your API Endpoint
    .set_project(os.environ.get('APPWRITE_FUNCTION_PROJECT_ID')) #Your Project ID available by default
    .set_key(os.environ.get('APPWRITE_API_KEY')) #Your secret API Key
)

# #Initialise the Sendgrid Client SDK
# sg = sendgrid.SendGridAPIClient(api_key=os.environ.get('SENDGRID_API_KEY')) #Your Sendgrid API Key

# Get the name and email of the new user from Appwrite's Environment variables
payload = json.loads(os.environ.get('APPWRITE_FUNCTION_DATA'))
recipient_id = payload['email']
print('recipient id: ', recipient_id)


list_id = os.environ.get('SENDGRID_LIST_ID') # The Newsletter ID to which the new user has to be added
print('list id: ', list_id)

url = "https://api.sendgrid.com/v3/marketing/contacts"
data = {
  "list_ids": [
    list_id
  ],
  "contacts": [
    {
      "email": recipient_id,
      "custom_fields": {}
    }
  ]
}


headers = {
    'authorization': "Bearer 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",
    'content-type': "application/json"
}

response = requests.request("PUT", url, data=json.dumps(data), headers=headers)

print(response.text)


