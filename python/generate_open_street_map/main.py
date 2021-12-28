from appwrite.client import Client
from appwrite.services.storage import Storage
import math
import requests
import os
import json

client = Client()
client.set_endpoint(os.environ["APPWRITE_ENDPOINT"]) 
client.set_project(os.environ["APPWRITE_FUNCTION_PROJECT_ID"]) # this is available by default
client.set_key(os.environ["APPWRITE_API_KEY"]) 
client.set_self_signed()

zoomLevel = 15


def coordinatesToTilePoint(latitude, longitude):
    x = math.floor((pow(2, zoomLevel) * ((longitude + 180) / 360)))
    radLat = math.radians(latitude)
    y = math.floor((pow(2, zoomLevel - 1) * (1 - math.log(math.tan(radLat)) + (1 / math.cos(radLat))) / math.pi))
    return (x,y)

def FetchTile(latitude, longitude):
    global point
    point = coordinatesToTilePoint(latitude, longitude) 
    tileUrl = 'https://tile.openstreetmap.org/{}/{}/{}.png'.format(zoomLevel, point[0], point[1]) 
    print(tileUrl)
    response = requests.get(tileUrl)
    if (response.status_code != 200):
        print('There was an error loading the tile ')
        exit(1)
    bytes = response.content
    return bytes

payload = json.loads(os.environ["APPWRITE_FUNCTION_DATA"])
longitude = payload["longitude"]
latitude = payload["latitude"]


imageBytes = FetchTile(latitude, longitude)
with open("Location.txt", "wb") as binary_file:
    binary_file.write(imageBytes)

def send_image():
    storage = Storage(client)
    result = storage.create_file(open('Location.txt','rb'))

try:
    print("Uploading your file....")
    send_image()
    print("Done!")
except Exception as e:
    print(e)

