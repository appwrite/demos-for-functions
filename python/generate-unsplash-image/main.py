import os
import requests


if os.environ.get("UNSPLASH_ACCESS_KEY", None) is None:
    sys.exit("Unsplash API key not set")

if os.environ.get("APPWRITE_FUNCTION_DATA", None) is None:
    sys.exit("Search keyword not provided")


try:
    searchEndpoint = "https://api.unsplash.com/search/photos?page=1&"
    queryString = "query=" + str(os.environ["APPWRITE_FUNCTION_DATA"])
    publicAuth = "&client_id=" + os.environ["UNSPLASH_ACCESS_KEY"]
    searchUrl = searchEndpoint + queryString + publicAuth

    searchResult = requests.get(searchUrl).json()

    firstResult = searchResult["results"][0]
    imageAuthor = firstResult["user"]["name"]
    imageUrl = firstResult["links"]["download"]

    res = {"imageUrl": imageUrl,
           "imageAuthor": imageAuthor}
    print(res)
except Exception as e:
    print(e)
