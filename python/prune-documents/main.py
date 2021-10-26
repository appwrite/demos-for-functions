# general imports
import os
from datetime import datetime

# dependencies
from appwrite.client import Client
from appwrite.services.database import Database

# Setup appwrite client
client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"]) 
client.set_project(os.environ["APPWRITE_FUNCTION_PROJECT_ID"]) # this is available by default
client.set_key(os.environ["APPWRITE_API_KEY"])

database = Database(client)

result_collections = database.list_collections()

# get seconds since epoch for the date from exactly 5 years ago for the filter
date_today = datetime.now()
date_old = datetime(date_today.year - 5, date_today.month, date_today.day, date_today.hour, date_today.minute, date_today.second)
seconds_since_epoch = int(date_old.timestamp())

# get ids of all collections
collections = result_collections['collections']
collection_list = []
for i in range(result_collections["sum"]):
    collection_list.append(collections[i]['$id'])

counter = 0

# get all documents older than 5 years and delete them
for i in collection_list:
    result_documents = database.list_documents(i, filters=[f"createdAt<={seconds_since_epoch}"])

    documents = result_documents["documents"]

    for j in range(result_documents["sum"]):
            result = database.delete_document(i, documents[j]["$id"])
            counter += 1

# print how many documents were deleted
if counter == 1:
    print(f"Successfully deleted {counter} old document")
elif counter > 1:
    print(f"Successfully deleted {counter} old documents")
else:
    print("No old documents found")