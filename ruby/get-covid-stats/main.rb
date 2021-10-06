# Import the helper libraries
require "uri"
require "net/http"
require "json"

# Get summary for all countries
summaryURL = URI('https://api.covid19api.com/summary')

# Parse the request
summary = JSON.parse(Net::HTTP.get(summaryURL))

# Global Data
globalData = summary["Global"]

# Data of All Countries 
countries = summary["Countries"]

# Get the country code from Appwrite's environment variable
payload = JSON.parse(ENV['APPWRITE_FUNCTION_DATA'])
COUNTRY_CODE = payload["COUNTRY_CODE"]

# Display Stats
def displayStats(data)
    cases = data["NewConfirmed"]
    deaths = data["NewDeaths"]
    recovered = data["NewRecovered"]
    puts "New cases: #{cases}"
    puts "New deaths: #{deaths}"
    puts "New recovery: #{recovered}"
end

if COUNTRY_CODE
    # Filtering country with COUNTRY_CODE
    countryData = countries.find {|x| x["CountryCode"] == COUNTRY_CODE}

    # Display Stats of the country
    country = countryData["Country"]
    puts "Covid stats of #{country} today:"
    displayStats(countryData)
else
    # Display Global Stats
    puts "Global Covid stats of today:"
    displayStats(globalData)
end 