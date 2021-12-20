# Perform all your imports

import os
import csv
import datetime
from appwrite.client import Client
from appwrite.services.database import Database
from appwrite.services.storage import Storage

# Initialise the Appwrite client
client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"])  # Your API Endpoint
client.set_project(os.environ["APPWRITE_FUNCTION_PROJECT_ID"])  # Your project ID
client.set_key(os.environ["APPWRITE_API_KEY"])  # Your secret API key

all_documents = []

user_id = os.environ['APPWRITE_FUNCTION_USER_ID']
user = f'user:{user_id}'

database = Database(client)
off_col = 0  # For the API pagination - collections
while True:
    collections = database.list_collections(limit=100, offset=off_col)
    for collection in collections['collections']:
        collection_id = collection['$id']
        off_doc = 0  # For the API pagination - documents
        while True:
            documents = database.list_documents(collection_id, limit=100, offset=off_doc)
            for document in documents['documents']:
                if user in document['$permissions']['read'] or '*' in document['$permissions']['read']:
                    all_documents.append(document)
            off_doc += 100
            if documents['sum'] <= off_doc:
                break

    off_col += 100
    if collections['sum'] <= off_col:
        break

# Create csv from the list of dictionaries
columns = []
for d in all_documents:
    for key in d.keys():
        if key not in columns:
            columns.append(key)
timestamp = datetime.datetime.now().isoformat()
file_name = f"{user_id}_{timestamp}.csv"
with open(file_name, 'w', newline='') as f:
    csv_writer = csv.DictWriter(f, fieldnames=columns, restval='')
    csv_writer.writeheader()
    csv_writer.writerows(all_documents)

storage = Storage(client)
result = storage.create_file(open(file_name, 'rb'), [user])
os.remove(file_name)
print('File {} created for user {}'.format(file_name, user_id))