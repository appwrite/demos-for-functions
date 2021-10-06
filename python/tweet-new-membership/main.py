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

# Load name of the new member
payload = json.loads(os.environ['APPWRITE_FUNCTION_EVENT_DATA'])
name = payload['name']



def tweetNewMembership():
    msg = 'Welcome! ' + name
    api.update_status(msg)
    print('Tweet sent!')

try:
    tweetNewMembership()
except Exception as e:
    print("Error in sending tweet!\n",e)