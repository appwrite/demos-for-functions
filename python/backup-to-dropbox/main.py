import sys
import csv
import os

import dropbox
from dropbox.files import WriteMode
from dropbox.exceptions import ApiError, AuthError

from appwrite.client import Client
from appwrite.services.database import Database


# Add OAuth2 access token here.
# You can generate one for yourself in the App Console.
# See <https://blogs.dropbox.com/developers/2014/05/generate-an-access-token-for-your-own-account/>
# TOKEN = ""
TOKEN = os.environ['DROPBOX_KEY']
PATH1 = 'data_file.csv'
PATH2 = 'data_file2.csv'
BACKUPPATH = '/' + PATH1

client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"]) 
client.set_project(os.environ["APPWRITE_FUNCTION_PROJECT_ID"]) # this is available by default.
client.set_key(os.environ["APPWRITE_API_KEY"]) 

# Create an instance of a Dropbox class, which can make requests to the API.
print("Creating a Dropbox object...")
with dropbox.Dropbox(TOKEN) as dbx:
    # Check that the access token is valid.
    try:
        dbx.users_get_current_account()
    except AuthError:
        sys.exit("ERROR: Invalid access token; try re-generating an "
            "access token from the app console on the web.")

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
    with open('data_file.csv', 'rb') as f:
        FileName = "data_file" + '-' + id + ".csv"
        BACKUPPATH = '/' + FileName
        # We use WriteMode=overwrite to make sure that the contents in the file are changed on upload
        print("Uploading " + FileName + " to Dropbox as " + BACKUPPATH + "...")
        try:
            print(BACKUPPATH)
            dbx.files_upload(f.read(), BACKUPPATH, mode=WriteMode('overwrite'))
        except ApiError as err:
            # This checks for the specific error where a user doesn't have
            # enough Dropbox space quota to upload this file
            if (err.error.is_path() and err.error.get_path().reason.is_insufficient_space()):
                sys.exit("ERROR: Cannot back up; insufficient space.")
            elif err.user_message_text:
                print(err.user_message_text)
                sys.exit()
            else:
                print(err)
                sys.exit()

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
    backup(fileID)


for x in collection_list:
    print(x)
    all_stuff(x)
    count2 = 0

print("Done!")
