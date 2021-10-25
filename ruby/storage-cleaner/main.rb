require 'appwrite'

# Set up client
client = Appwrite::Client.new
client
    .set_endpoint(ENV['APPWRITE_ENDPOINT']) # Your API Endpoint
    .set_project(ENV['APPWRITE_FUNCTION_PROJECT_ID']) # Your project ID available by default
    .set_key(ENV['APPWRITE_API_KEY']) # Your secret API key

storage = Appwrite::Storage.new(client)

days_to_expire = ENV['DAYS_TO_EXPIRE'].to_i
expiry_date = Time.now - (days_to_expire * 24 * 60 * 60) # Converting days into seconds

num_deleted = 0
begin
    files_in_storage = storage.list_files(limit: 100)

    files_in_storage['files'].each do |file|
        # Delete file if it's expired
        next unless file['dateCreated'] < expiry_date.to_i

        storage.delete_file(file_id: file['$id'])
        num_deleted += 1

        puts "Deleted file #{file['name']}"
    end
rescue Appwrite::Exception => e
    puts e.message
end

puts "Total files deleted: #{num_deleted}"
