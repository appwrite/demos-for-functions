# frozen_string_literal: true

require 'appwrite'
require 'httparty'
require 'json'

# Set up client
client = Appwrite::Client.new
client
  .set_endpoint(ENV['APPWRITE_ENDPOINT']) # Your API Endpoint
  .set_project(ENV['APPWRITE_FUNCTION_PROJECT_ID']) # Your project ID available by default
  .set_key(ENV['APPWRITE_API_KEY']) # Your secret API key

# Get the name and email of the new user from Appwrite's Environment variables
payload = JSON.parse(ENV['APPWRITE_FUNCTION_DATA'])
email = payload['email']

SENDGRID_LIST_ID = ENV['SENDGRID_LIST_ID'] # The Newsletter ID to which the new user has to be added

data = {
  'list_ids' => [SENDGRID_LIST_ID],
  'contacts' => [{ "email": email, "custom_fields": {} }]
}

headers = {
  'Authorization': "Bearer #{ENV['SENDGRID_API_KEY']}",
  'Content-Type': 'application/json'
}

response = HTTParty.put('https://api.sendgrid.com/v3/marketing/contacts', body: data.to_json, headers: headers)
puts response.body
