import sys
import json
import csv

import dropbox
from dropbox.files import WriteMode
from dropbox.exceptions import ApiError, AuthError

from appwrite.client import Client
from appwrite.services.database import Database

# Converts JSON file to .CSV format
def JSON_to_CSV(list_db):
    document_data = list_db['documents']

    # now we will open a file for writing
    data_file = open('data_file.csv', 'w')

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

# Uploads contents of FILENAME to Dropbox
def backup():
    with open('data_file.csv', 'rb') as f:
        # We use WriteMode=overwrite to make sure that the contents in the file are changed on upload
        print("Uploading " + FILENAME + " to Dropbox as " + BACKUPPATH + "...")
        try:
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


# Add OAuth2 access token here.
# You can generate one for yourself in the App Console.
# See <https://blogs.dropbox.com/developers/2014/05/generate-an-access-token-for-your-own-account/>
TOKEN = 'DROPBOX_KEY'
FILENAME = 'data_file.csv'
BACKUPPATH = '/data_file.csv'

# Setup the appwrite SDK
client = Client()
client.set_endpoint("APPWRITE_ENDPOINT") 
client.set_project("APPWRITE_FUNCTION_PROJECT_ID") # this is available by default
client.set_key("APPWRITE_API_KEY") 


# Get the ID of the uploaded file from the environment variable set by appwrite.
payload = json.loads("APPWRITE_FUNCTION_EVENT_DATA")# your json of collection id
fileID = payload["$id"]

database = Database(client)
list_db = database.list_documents(fileID)

JSON_to_CSV(list_db)

# Check if we have the access token 
if (len(TOKEN) == 0):
    sys.exit("ERROR: Looks like you didn't add your access token. "
        "Open up backup-and-restore-example.py in a text editor and "
        "paste in your token in line 14.")

# Create an instance of a Dropbox class, which can make requests to the API.
print("Creating a Dropbox object...")
with dropbox.Dropbox(TOKEN) as dbx:
    # Check that the access token is valid
    try:
        dbx.users_get_current_account()
    except AuthError:
        sys.exit("ERROR: Invalid access token; try re-generating an "
            "access token from the app console on the web.")

    # Create a backup of the current settings file
    backup()
    print("Done!")