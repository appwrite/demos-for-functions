require 'date'
require 'json'
require 'appwrite'

def main
  client = Appwrite::Client.new()

  client
    .set_endpoint(ENV['APPWRITE_ENDPOINT'])
    .set_project(ENV['APPWRITE_FUNCTION_PROJECT_ID'])
    .set_key(ENV['APPWRITE_API_KEY'])
  ;

  data = JSON.parse(ENV['APPWRITE_FUNCTION_EVENT_DATA'])
  id = data['$id']
  collection = data['$collection']

  if data['updatedAt']
  
    date = DateTime.now
    
    begin
      # Abort if the document_date is too close to the current date (< 1 second) [prevents looping]
      document_date = DateTime.strptime(data['updatedAt'], '%Y-%m-%dT%H:%M:%S.%LZ')
      if ((date - document_date) * 24 * 60 * 60).to_i <= 0
        puts 'Aborting: UpdatedAt is too close to the current date'
        return
      end
    rescue
    end

    database = Appwrite::Database.new(client);

    # Convert date to ISO 8601 format
    date = date.strftime('%Y-%m-%dT%H:%M:%S.%LZ')

    database.update_document(collection_id: collection, document_id: id, data: { 'updatedAt' => date })
    puts "Collection: #{collection} document: #{id} set updatedAt to: #{date}"
  end

end

main()



