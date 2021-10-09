import csv
import os

from appwrite.client import Client
from appwrite.services.database import Database
from appwrite.services.storage import Storage

PATH1 = 'data_file.csv'
PATH2 = 'data_file2.csv'
BACKUPPATH = '/' + PATH1

client = Client()
client.set_endpoint("http://192.168.1.9/v1") 
client.set_project("615d8b0e5f3f3") # this is available by default.
client.set_key("98819b363864e759f998ad32d5c533c0790516b44bc65572511c7b57e6f6b6b7b0ed6387c323aa0d03b01a8d148f57712a72862471821d58f5da570d72139844ba8ef1792b6990d79f5185e989b2819ea85f3e8f0a2b81f181de3797c417d244447d0e6996624f31d3da13f0685272d6ca84a72515536a152b63cf391958f429") 


# # Setup the appwrite SDK
client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"]) 
client.set_project(os.environ["APPWRITE_FUNCTION_PROJECT_ID"]) # this is available by default
client.set_key(os.environ["APPWRITE_API_KEY"]) 

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
def backup(id):
    FileName = "data_file-" + id + ".csv"
    os.rename(PATH1, FileName)
    storage = Storage(client)
    result_upload = storage.create_file(open(FileName, 'rb'))


database = Database(client)
result = database.list_collections()

collections = result['collections']
collection_list = []
name_list = []
for i in range(result['sum']):
    collection_list.append(collections[i]['$id'])
    name_list.append(collections[i]['name'])

def all_stuff(file_id):
    fileID = file_id
    global PATH1

    limit = 100 # Max number of documents we can request at a time from appwrite.
    result_dc = database.list_documents(fileID, limit = limit)

    sum = result_dc["sum"]
    j = int(sum/limit) # This will help us to run a loop for j number of times to get all the data from collection.

    JSON_to_CSV(result_dc, PATH1) # We will atleast convert our data to .csv once.

    if(j > 0): # If all the data can't be acquired from one request (more than 100 data in collection), then we will call it in batches.
        for i in range(1, j): # As we have already requested for collection once already, we will start the loop from 1.
            list = database.list_documents(fileID, limit=limit, offset=(i)*limit) 
            JSON_to_CSV(list, PATH2)
            data1 = open(PATH1, 'a+')        
            data2 = open(PATH2, 'r')       
            data1 = data1.write("\n" + data2.read()) 

    # Create a backup of the current settings file.
    try:
        backup(fileID)
        print(fileID + " is being uploaded")
    except Exception as e:
        print(e)


for x in collection_list:
    all_stuff(x)
    count2 = 0

print("Done!")

