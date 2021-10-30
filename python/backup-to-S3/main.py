import csv
import os

import boto3

from appwrite.client import Client
from appwrite.services.database import Database


# Add OAuth2 access token here.
# You can generate one for yourself in the App Console.
TOKEN = os.environ['ACCESS_TOKEN']
TOKEN_SECRET = os.environ['ACCESS_KEY_SECRET']
BUCKET = os.environ['BUCKET_ID']
PATH1 = 'data_file.csv'
PATH2 = 'data_file2.csv'
BACKUPPATH = '/' + PATH1

client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"]) 
client.set_project(os.environ["APPWRITE_FUNCTION_PROJECT_ID"]) # this is available by default.
client.set_key(os.environ["APPWRITE_API_KEY"]) 


# Creating Session With Boto3.
session = boto3.Session(
aws_access_key_id=TOKEN,
aws_secret_access_key=TOKEN_SECRET
)

s3 = session.resource('s3')


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
        print("Uploading " + FileName + " to AWS. ")
        try:
            print(BACKUPPATH)
            result = s3.meta.client.upload_file(Filename='data_file.csv', Key=FileName, Bucket=BUCKET)

        except Exception as e:
            print(e)

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
