# Perform all your imports
from appwrite.client import Client
from appwrite.services.database import Database
import pandas as pd
from appwrite.services.storage import Storage

# Initialise the Appwrite client
client = Client()
client.set_endpoint('https://localhost/v1') # Your API Endpoint
client.set_project('615aea9a672b6') # Your project ID
client.set_key('a2359078b4ec35e27b3146bdc8a7913e9f5dc57ae82a6f7deb55a970dbebaf919333df99f4a054a9ab9ac66f3158fbb756c994711285479b76ce68e49f08b867ab5e0e9668c840fa6f27f593c41b984389b708ce75165f826c6fc94835aa8219a43db28a63d83fd7eacbc4105acaea7db993c0150167ea111a728731763b0db6') # Your secret API key

all_documents = []

user = 'user:ppp'
database = Database(client)
off_col = 0
while True:
    collections = database.list_collections(limit=100,offset=off_col)
    for collection in collections['collections']:
        collection_id = collection['$id']
        off_doc=0
        while True:
            print(off_col,off_doc)
            documents =  database.list_documents(collection_id,limit=100,offset=off_doc)
            for document in documents['documents']:
                if user in document['$permissions']['read'] or '*' in document['$permissions']['read']:
                    all_documents.append(document)
            off_doc += 100
            if documents['sum']<=off_doc:
                break

    off_col += 100
    if collections['sum']<=off_col:
        break


final_csv = pd.DataFrame(all_documents)
final_csv.to_csv('asdf.csv')


storage = Storage(client)

result = storage.create_file(open('asdf.csv', 'rb'),[user])