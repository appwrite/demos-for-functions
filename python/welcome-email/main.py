import requests
import os
import json

payload = json.parse(os.environ.get('APPWRITE_FUNCTION_EVENT_PAYLOAD'))
name = payload['name']
email = payload['email']


from dotenv import load_dotenv

load_dotenv()

basedir = os.path.abspath(os.path.dirname(__file__))
key = os.environ.get('MAILGUN_API_KEY')
domain = os.environ.get('MAILGUN_DOMAIN')

request_url = 'https://api.mailgun.net/v2/{0}/messages'.format(Domain)
request = requests.post(request_url, auth=('api', key), data={
    'from': 'Welcome to My Awesome App <welcome@my-awesome-app.io>',
    'to': email,
    'subject': 'Welcome on board {0}!'.format(name),
    'text': 'Hi {0}\nGreat to have you with us. ! 😍'.format(name)
})
