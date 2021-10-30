# frozen_string_literal: true

require 'appwrite'
require 'json'

# Set up client
client = Appwrite::Client.new
client
  .set_endpoint(ENV['APPWRITE_ENDPOINT']) # Your API Endpoint
  .set_project(ENV['APPWRITE_FUNCTION_PROJECT_ID']) # Your project ID available by default
  .set_key(ENV['APPWRITE_API_KEY']) # Your secret API key

# Set up the database connection
database = Appwrite::Database.new(client)

# Parse function event data
event_data = JSON.parse(ENV['APPWRITE_FUNCTION_EVENT_DATA'])
document_id = event_data['$id']
collection_id = event_data['$collection']

# Compute the current date in ISO format
ISO_FORMAT = '%Y-%m-%dT%H:%M:%S.%L%z'
iso_date = Time.now.strftime(ISO_FORMAT)

# Update the document, showing the response
response = database.update_document(
  collection_id: collection_id,
  document_id: document_id,
  data: { 'updatedAt': iso_date }
)

puts response
