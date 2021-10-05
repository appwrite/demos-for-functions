import os
import requests
import sys

API_ENDPOINT = 'https://api.covid19api.com'
COUNTRY_CODE = os.environ.get('APPWRITE_FUNCTION_DATA', "")
MESSAGE_TEMPLATE = """COVID-19 stats for {}:
Confirmed cases today: {}
Recovered today: {}
Deaths today: {}
"""

# Parses COVID-19 stats and prints them.
def print_covid_stats(statsobj):
    country_name = statsobj.get('Country', 'the world')
    confirmed_cases = statsobj['NewConfirmed']
    recovered_today = statsobj['NewRecovered']
    deaths_today = statsobj['NewDeaths']
    msg = MESSAGE_TEMPLATE.format(country_name, confirmed_cases, recovered_today, deaths_today)
    print(msg)

# Fetches COVID-19 data. Uses ISO2 country code parameter.
def get_covid_stats(country_code):
    global_summary = requests.get(f'{API_ENDPOINT}/summary').json()
    if not country_code:
        print_covid_stats(global_summary['Global'])
        return
    for country in global_summary['Countries']:
        if country['CountryCode'] == country_code:
            print_covid_stats(country)
            return
    sys.exit(f"ERROR: Couldn't find country code {country_code}.")

get_covid_stats(COUNTRY_CODE)
