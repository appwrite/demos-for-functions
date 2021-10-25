import os
import json
import time

from appwrite.client import Client
from appwrite.services.database import Database


def init_client():
    # Initialize the Appwrite client
    client = Client()
    client.set_endpoint(os.getenv("APPWRITE_ENDPOINT"))
    client.set_project(os.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
    client.set_key(os.getenv("APPWRITE_API_KEY"))

    return client

def main():
    payload = json.loads(os.getenv("APPWRITE_FUNCTION_EVENT_DATA"))

    UPDATED_AT_KEY = "updatedAt"
    collectionId = payload["$collection"]
    documentId = payload["$id"]
    currentTimeStamp = time.time()

    if UPDATED_AT_KEY not in payload:
        return

    client = init_client()
    database = Database(client)

    database.update_document(
        collectionId,
        documentId,
        {UPDATED_AT_KEY: currentTimeStamp},
    )
    
    print("Updated the field updatedAt with current timestamp successfully.")

if __name__ == "__main__":
    main()
