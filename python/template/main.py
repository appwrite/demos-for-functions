# Perform all your imports
from appwrite.client import Client
from appwrite.services.storage import Storage

# Initialise the Appwrite client
client = Client()
client.set_endpoint('https://[HOSTNAME_OR_IP]/v1') # Your API Endpoint
client.set_project('5df5acd0d48c2') # Your project ID
client.set_key('919c2d18fb5d4...a2ae413da83346ad2') # Your secret API key

# Initialise the sttorage SDK with the client object
storage = Storage(client)

# Perform your task
result = storage.list_files()