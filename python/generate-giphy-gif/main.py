import os
import sys

import requests

if os.environ.get("GIPHY_API_KEY", None) is None:
    sys.exit("GIPHY API key not set")

if os.environ.get("APPWRITE_FUNCTION_DATA", None) is None:
    sys.exit("Search query not provided")

try:
    endpoint = f"https://api.giphy.com/v1/gifs/search?api_key={os.environ['GIPHY_API_KEY']}&q={os.environ['APPWRITE_FUNCTION_DATA']}&limit=1"
    response = requests.get(endpoint)
    response.raise_for_status()
    url = response.json()["data"][0]['url']
    print(url)
except Exception as e:
    print(e)
