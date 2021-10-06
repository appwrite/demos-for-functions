import os
import json
import math
import time

from appwrite.client import Client
from appwrite.services.database import Database
from appwrite.exception import AppwriteException


def init_client():
    """Initialize the Appwrite client"""
    client = Client()
    client.set_endpoint(os.getenv("APPWRITE_ENDPOINT"))
    client.set_project(os.getenv("APPWRITE_FUNCTION_PROJECT_ID"))
    client.set_key(os.getenv("APPWRITE_API_KEY"))

    return client


def main():
    EVENT_DATA = json.loads(os.getenv("APPWRITE_FUNCTION_EVENT_DATA"))

    CREATED_AT_KEY = os.getenv("CREATED_AT_KEY", "createdAt")

    client = init_client()
    database = Database(client)

    document_id = EVENT_DATA["$id"]
    collection_id = EVENT_DATA["$collection"]

    try:
        result = database.update_document(
            collection_id,
            document_id,
            {CREATED_AT_KEY: math.floor(time.time())},
        )
        print(
            f"Updated {CREATED_AT_KEY} on document {document_id} to {result[CREATED_AT_KEY]}"
        )
    except AppwriteException as err:
        if err.code != 400 or CREATED_AT_KEY not in err.message:
            raise

        print(f"Collection {collection_id} does not have {CREATED_AT_KEY} key")


if __name__ == "__main__":
    main()
