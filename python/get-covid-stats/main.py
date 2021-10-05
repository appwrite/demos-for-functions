from datetime import datetime
import os
import requests
import sys

API_ENDPOINT = 'https://api.covid19api.com'
# COUNTRY_CODE = os.environ['APPWRITE_FUNCTION_DATA']

# Returns a map from country code to country "slug" used in API requests.
def get_countries_map():
    response = requests.get(f'{API_ENDPOINT}/countries').json()
    slugs = dict()
    for country in response:
        country_code = country['ISO2']
        country_slug = country['Slug']
        slugs[country_code] = country_slug
    return slugs

# Fetches COVID-19 data. Uses ISO2 country code parameter.
def get_covid_stats(country_code):
    countries_map = get_countries_map()
    # Validate country code by looking it up in countries map
    if country_code not in countries_map:
        sys.exit(f'ERROR: Country code {country_code} is invalid.')
    country_slug = countries_map[country_code]
    f, t = get_time_range()
    response = requests.get(f'{API_ENDPOINT}/country/{country_slug}/status/confirmed/date/{f}')
    # https://api.covid19api.com/country/south-africa/status/confirmed?from=2020-03-01T00:00:00Z&to=2020-04-01T00:00:00Z

# Returns from and to dates formatted in ISO8601
def get_time_range():
    _from = datetime.now().replace(hour=0,minute=0,second=0,microsecond=0).isoformat()
    to = datetime.now().replace(microsecond=0).isoformat()
    return _from, to

# get_covid_stats("UA")
a, b = get_time_range()
print(a)
print(b)
