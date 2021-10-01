# general imports
from gtts import gTTS
import json
import os

# Perform all your imports
# Appwrite SDK
from appwrite.client import Client
from appwrite.services.storage import Storage

#----------------------------------------------------
def text2speech(text,lang='en'):
    myobj = gTTS(text=TEXT, lang=lang, slow=False)
    return myobj
#----------------------------------------------------

FILENAME = "temp.mp3"

# Triggered by the storage.files.create event
payload = json.loads(os.environ["APPWRITE_FUNCTION_EVENT_DATA"])
TEXT = payload["$id"]

# Setup appwrite client
client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"])
client.set_project(os.environ["APPWRITE_FUNCTION_PROJECT_ID"]) # this is available by default
client.set_key(os.environ["APPWRITE_API_KEY"])

# Initialise the storage SDK with the client object
storage = Storage(client)

# text to speech
myobj = text2speech(TEXT)
myobj.save(FILENAME)

# Save the file to the container
result = storage.create_file(open(FILENAME, 'rb'))
