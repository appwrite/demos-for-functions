#Importing the required libraries
import sendgrid
import os
import json
from appwrite.client import Client

#Initialise the Appwrite Client SDK for Python
client = Client()

(client
    .set_endpoint(os.environ.get('APPWRITE_ENDPOINT')) #Your API Endpoint
    .set_project(os.environ.get('APPWRITE_FUNCTION_PROJECT_ID')) #Your Project ID available by default
    .set_key(os.environ.get('APPWRITE_API_KEY')) #Your secret API Key
)

#Initialise the Sendgrid Client SDK
sg = sendgrid.SendGridAPIClient(api_key=os.environ.get('SENDGRID_API_KEY')) #Your Sendgrid API Key

# Get the name and email of the new user from Appwrite's Environment variables
payload = json.loads(os.environ.get('APPWRITE_FUNCTION_DATA'))
recipient_id = payload['email']
print('recipient id: ', recipient_id)


list_id = os.environ.get('SENDGRID_LIST_ID') # The Newsletter ID to which the new user has to be added
print('list id: ', list_id)

# Add a Single Recipient to a List #
# POST /contactdb/lists/{list_id}/recipients/{recipient_id} #
try:
    response = sg.client.contactdb.lists._(
     list_id
    ).recipients._(recipient_id).post()

    print(response.status_code)
    print(response.body)

except Exception:
    #Error handling
    pass

