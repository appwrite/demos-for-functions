# Import the helper libraries
require 'uri'
require 'net/http'
require 'json'

# Fetch the neccessary client_id and the query, creating the unsplash_endpoint we are going to request for
client_id = ENV['UNSPLASH_ACCESS_KEY']
query = JSON.parse(ENV['APPWRITE_FUNCTION_DATA']).get('query', '')
unsplash_endpoint = URI("https://api.unsplash.com/search/photos?client_id=#{client_id}&query=#{query}&page=1")

# Parse the request
response = JSON.parse(Net::HTTP.get(unsplash_endpoint))
result = response['results'].first

puts({ image_author: result&.user&.name, image_url: result&.links&.download }.to_json)
