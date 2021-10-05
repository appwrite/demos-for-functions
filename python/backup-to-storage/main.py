from datetime import datetime
import json
import csv
import os
import pandas as pd

from dropbox.files import WriteMode
from dropbox.exceptions import ApiError, AuthError

from appwrite.client import Client
from appwrite.services.database import Database
from appwrite.services.storage import Storage

def JSON_to_CSV(list_db, filepath):
    document_data = list_db['documents']

    # now we will open a file for writing
    data_file = open(filepath, 'w')

    # create the csv writer object
    csv_writer = csv.writer(data_file)

    # Counter variable used for writing headers to the CSV file
    count = 0

    for doc in document_data:
        if count == 0:

            # Writing headers of CSV file
            header = doc.keys()
            csv_writer.writerow(header)
            count += 1

        # Writing data of CSV file
        csv_writer.writerow(doc.values())

    data_file.close()

# Uploads contents of PATH1 to Appwrite Storage
def backup():
    now = datetime.now()
    date_today = now.strftime("%d-%m-%Y") # Gets the date of today for naming of the csv file 
    FileName = "data_file-" + date_today + ".csv"
    os.rename(PATH1, FileName)
    storage = Storage(client)
    result_upload = storage.create_file(open(FileName, 'rb'))



PATH1 = 'data_file.csv'
PATH2 = 'data_file2.csv'
BACKUPPATH = '/data_file1.csv'

# Setup the appwrite SDK
client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"]) 
client.set_project(os.environ["APPWRITE_FUNCTION_PROJECT_ID"]) # this is available by default.
client.set_key(os.environ["APPWRITE_API_KEY"]) 


# Get the ID of the uploaded file from the environment variable set by appwrite.
payload = json.loads(os.environ["APPWRITE_FUNCTION_EVENT_DATA"])# your json of collection id.
fileID = payload["$id"]

database = Database(client)
limit = 100 # Max number of documents we can request at a time from appwrite.
result = database.list_documents(fileID, limit = limit)

sum = result["sum"]
j = int(sum/limit) # This will help us to run a loop for j number of times to get all the data from collection.
data = result['documents']
for doc in data:
    keys = doc.keys() # Provides all the column names or keys of the document/database.

JSON_to_CSV(result, PATH1) # We will atleast convert our data to .csv once.

if(j > 0): # If all the data can't be acquired from one request (more than 100 data in collection), then we will call it in batches.
    for i in range(1, j): # As we have already requested for collection once already, we will start the loop from 1.
        list = database.list_documents(fileID, limit=limit, offset=(i)*limit) 
        JSON_to_CSV(list, PATH2)
        data1 = pd.read_csv(PATH1)         # You can extract the information from the JSON using the document key and append it to an empty list 
        data2 = pd.read_csv(PATH2)         # iteratively. But it will from a list of list and converting it to a .csv was prooving to be quite inconsistent.
        data1 = pd.concat([data1, data2]) 
        data1.to_csv(PATH1)

data1 = pd.read_csv(PATH1)
data = data1[keys] # We will only take the required columns from the dataset.
data.to_csv(PATH1) 


print("Uploading to your storage...")

backup()
print("Done!")
