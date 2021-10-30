from appwrite.client import Client
from appwrite.services.storage import Storage

import tinify

import os

client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"]) 
client.set_project(os.environ["APPWRITE_FUNCTION_PROJECT_ID"]) # this is available by default
client.set_key(os.environ["APPWRITE_API_KEY"]) 



def get_image():
    tinify.key = os.environ["TINIFY_KEY"]
    source = tinify.from_url(os.environ["IMAGE_URL"])
    source.to_file("optimized.png")

def send_image():
    storage = Storage(client)
    result = storage.create_file(open('optimized.png', 'rb'))

get_image()

try:
    print("Uploading your file....")
    send_image()
    print("Done!")
except Exception as e:
    print(e)
