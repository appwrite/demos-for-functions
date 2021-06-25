import sys
import json
import os

import dropbox
from dropbox.files import WriteMode
from dropbox.exceptions import ApiError, AuthError
from appwrite.client import Client
from appwrite.services.storage import Storage

# Uploads contents of FILENAME to Dropbox
def backup():
    with open(FILENAME, 'rb') as f:
        # We use WriteMode=overwrite to make sure that the contents in the file
        # are changed on upload
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
TOKEN = os.environ['DROPBOX_KEY']
FILENAME = 'my-file.txt'
BACKUPPATH = '/my-file.txt'

# Setup the appwrite SDK
client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"]) 
client.set_project(os.environ["APPWRITE_FUNCTION_PROJECT_ID"]) # this is available by default
client.set_key(os.environ["APPWRITE_API_KEY"]) 

# Get the ID of the uploaded file from the environment variable set by appwrite.
payload = json.loads(os.environ["APPWRITE_FUNCTION_EVENT_PAYLOAD"])
fileID = payload["$id"]

# Create an instance of Appwrite's Storage API
storage = Storage(client)
result = storage.get_file_download(fileID)

# Save the file to the container
with open(FILENAME, "wb") as newFile:
    newFile.write(result)

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
