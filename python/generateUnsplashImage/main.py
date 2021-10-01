import os
import requests

client_id=YOUR_ACCESS_KEY

def getUnsplashImage(keyword):
    searchEndpoint = "https://api.unsplash.com/search/photos?page=1&"
    queryString = "query=" + str(keyword)
    publicAuth = "client_id=" + os.environ["UNSPLASH_ACCESS_KEY"]
    searchUrl = searchEndpoint + queryString + publicAuth

    response = requests.get(searchUrl)

    if (response.status_code == 200):
        searchResult = response.json()

        firstResult = searchResult["results"][0]
        imageAuthor = firstResult["user"]["name"]
        imageUrl = firstResult["links"]["download"]

    else:
        imageAuthor = "Could not search the keyword!"
        imageUrl = "Could not search the keyword!"

    return imageUrl, imageAuthor
