import tweepy
from os 
import json

# Twitter API keys
key = os.environ['TWITTER_KEY']
secret = os.environ['TWITTER_SECRET']
access_token= os.environ['TWITTER_access_token']
access_token_secret = os.environ['TWITTER_access_token_secret']

# Twitter API client 
auth = tweepy.OAuthHandler(key, secret)
auth.set_access_token(access_token, access_token_secret)
api = tweepy.API(auth)

def tweetNewMembership():
    msg = "A new user has joined a team!"
    api.update_status(msg)
    print('Tweet sent!')

try:
    tweetNewMembership()
except Exception as e:
    print("Error in sending tweet!\n",e)