# Perform all your imports
from appwrite.client import Client
from appwrite.services.database import Database
from appwrite.services.storage import Storage

# Initialise the Appwrite client
client = Client()
client.set_endpoint('https://localhost/v1') # Your API Endpoint
client.set_project('615aea9a672b6') # Your project ID
client.set_key('df3da8f38df454301e10fb190aec6a40ef5a59b8643173c1ee0d73bae9308464fef6e2b090a781fafc2b5380bf556a84b74c26a48dfa0c9e6c9bd37c385d9e15cf9f07e5682fd4c489dbf78fddf56d994bdba1ebef414768e6625b691a4e3d702ffd011f7bc5f187c8ee3ac01759affebc81417683d34bf39e6c04e29016a2be') # Your secret API key

database = Database(client)

actors = database.create_collection(
    'Actors',
    ['*'],
    ['user:ppp','team:xxx'],
    [
        {
          "label": "Name",
          "key": "name",
          "type": "text",
          "default": "Empty Name",
          "required": True,
          "array": False
        },
    ]
)
movies = database.create_collection('Movies', # Collection Name
      ['*'], # Read permissions
      ['user:ppp', 'team:xxx'], # Write permissions
      [ #Rules
        {
          "label": "Name",
          "key": "name",
          "type": "text",
          "default": "Empty Name",
          "required": True,
          "array": False
        },
        {
          "label": "Release Year",
          "key": "releaseYear",
          "type": "numeric",
          "default": 1970,
          "required": True,
          "array": False
        },
        {
          "label": "Actors",
          "key": "actors",
          "type": "document",
          "default": None,
          "required": False,
          "array": True,
          "list": [actors['$id']] # Name the collections unique IDs that are allowed in the attribute
        }
      ]
  )
result = database.create_document(
    movies['$id'],
    {
        "name": "Frozen 2",
        "releaseYear": 2019,
        "actors":
            [
                {
                    "$collection": actors['$id'], # The actors collection unique ID
                      "$permissions": {"read": ["*"], "write": ['user:ppp', 'team:xxx']}, # Set document permissions
                      "name": "Idina Menzel"
                },
        {
          "$collection": actors['$id'], # The actors collection unique ID
          "$permissions": {"read": ["*"], "write": ['user:ppp', 'team:xxx']}, # Set document permissions
          "name": "Kristen Bell"
        }
      ]
    },
    ['*'])


authors = database.create_collection(
    'Authors',
    ['*'],
    ['user:ppp','team:qqq'],
    [
        {
          "label": "Name",
          "key": "name",
          "type": "text",
          "default": "Empty Name",
          "required": True,
          "array": False
        },
    ]
)
books = database.create_collection('Books', # Collection Name
      ['*'], # Read permissions
      ['user:ppp', 'team:qqq'], # Write permissions
      [ #Rules
        {
          "label": "Name",
          "key": "name",
          "type": "text",
          "default": "Empty Name",
          "required": True,
          "array": False
        },
        {
          "label": "Release Year",
          "key": "releaseYear",
          "type": "numeric",
          "default": 1970,
          "required": True,
          "array": False
        },
        {
          "label": "Authors",
          "key": "authors",
          "type": "document",
          "default": None,
          "required": False,
          "array": True,
          "list": [authors['$id']] # Name the collections unique IDs that are allowed in the attribute
        }
      ]
  )
result_2 = database.create_document(
    books['$id'],
    {
        "name": "cronicas de dragonlance",
        "releaseYear": 2019,
        "authors":
            [
                {
                    "$collection": authors['$id'], # The actors collection unique ID
                      "$permissions": {"read": ["*"], "write": ['user:ppp', 'team:qqq']}, # Set document permissions
                      "name": "Margaret"
                },
        {
          "$collection": authors['$id'], # The actors collection unique ID
          "$permissions": {"read": ["*"], "write": ['user:ppp', 'team:qqq']}, # Set document permissions
          "name": "Tracy"
        }
      ]
    },
    ['*'])