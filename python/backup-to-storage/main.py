from datetime import datetime
import csv
import os

from appwrite.client import Client
from appwrite.services.database import Database
from appwrite.services.storage import Storage


count2 = 0 # this will prevent the formation of repetative header files if the collection has more records than limit
def JSON_to_CSV(list_db, filepath):
    document_data = list_db['documents']

    # now we will open a file for writing
    data_file = open(filepath, 'w')

    # create the csv writer object
    csv_writer = csv.writer(data_file)

    # Counter variable used for writing headers to the CSV file
    count = 0
    global count2
    
    for doc in document_data:
        if count == 0 and count2 == 0:

            # Writing headers of CSV file
            header = doc.keys()
            csv_writer.writerow(header)
            count += 1

        # Writing data of CSV file
        csv_writer.writerow(doc.values())

    data_file.close()

# Uploads contents of PATH1 to Dropbox
def backup():
    now = datetime.now()
    date_today = now.strftime("%d-%m-%Y")
    FileName = "data_file-" + date_today + ".csv"
    os.rename(PATH1, FileName)
    storage = Storage(client)
    result_upload = storage.create_file(open(FileName, 'rb'))


# Add OAuth2 access token here.
# You can generate one for yourself in the App Console.
# See <https://blogs.dropbox.com/developers/2014/05/generate-an-access-token-for-your-own-account/>
PATH1 = 'data_file.csv'
PATH2 = 'data_file2.csv'
BACKUPPATH = '/data_file1.csv'

# Setup the appwrite SDK
client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"]) 
client.set_project(os.environ["APPWRITE_FUNCTION_PROJECT_ID"]) # this is available by default
client.set_key(os.environ["APPWRITE_API_KEY"]) 


# Provide the ID of the collection for making backup
fileID = os.environ["COLLECTION_ID"]

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
        data1 = open(PATH1, 'a+')        
        data2 = open(PATH2, 'r')       
        data1 = data1.write("\n" + data2.read())


print("Uploading to your storage...")

try:
    backup()
except Exception as e:
    print(e)

print("Done!")
