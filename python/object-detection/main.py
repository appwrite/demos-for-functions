from pprint import pprint
import json 
import os

# For cloud vision API
import cloudmersive_image_api_client
from cloudmersive_image_api_client.rest import ApiException

# Appwrite SDK
from appwrite.client import Client
from appwrite.services.storage import Storage

FILENAME = "temp.jpg"

# Triggered by the storage.files.create event
payload = json.loads(os.environ["APPWRITE_FUNCTION_EVENT_PAYLOAD"])
fileID = payload["$id"]

# Setup appwrite client
client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"]) 
client.set_project(os.environ["APPWRITE_PROJECT_ID"]) 
client.set_key(os.environ["APPWRITE_API_KEY"])

# Get the image file 
storage = Storage(client)
result = storage.get_file_preview(fileID)

# Save the file to the container
with open(FILENAME, "wb") as newFile:
    newFile.write(result)

# Configure API key authorization: Apikey
configuration = cloudmersive_image_api_client.Configuration()
configuration.api_key['Apikey'] = os.environ['CLOUDMERSIVE_API_KEY']

# create an instance of the API class
api_instance = cloudmersive_image_api_client.RecognizeApi(cloudmersive_image_api_client.ApiClient(configuration))
image_file = FILENAME # file | Image file to perform the operation on.  Common file formats such as PNG, JPEG are supported.

try:
    # Detect objects including types and locations in an image
    api_response = api_instance.recognize_detect_objects(image_file)
    pprint(api_response)
except ApiException as e:
    print("Exception when calling RecognizeApi->recognize_detect_objects: %s\n" % e)
