# general imports
import os
from datetime import datetime, timedelta

# dependencies
from appwrite.client import Client
from appwrite.services.storage import Storage

# Setup appwrite client
client = Client()
client.set_endpoint(os.environ['APPWRITE_ENDPOINT'])
client.set_project(os.environ['APPWRITE_FUNCTION_PROJECT_ID']) # this is available by default
client.set_key(os.environ['APPWRITE_API_KEY'])

# storage object for given client
storage = Storage(client)

# gather all files available in given storage
result = storage.list_files(limit=100)

# datetime objects to compare with time of files
days = int(os.environ['DAYS_TO_EXPIRE'])
delete_at = datetime.now() - timedelta(days=days)

deleted_files = 0

# iterating over files in storage and deleting according constraints
for each in result['files']:
    if each['dateCreated'] < delete_at.timestamp():
        storage.delete_file(each['$id'])
        deleted_files += 1

print(f"Total files deleted: {deleted_files}")
