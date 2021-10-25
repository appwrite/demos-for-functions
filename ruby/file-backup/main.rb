require 'json'
require 'dropbox_api'
require 'appwrite'

def get_file(client, file_id)
	begin
		storage = Appwrite::Storage.new(client)

		response = storage.get_file_preview(file_id: file_id)
		response
	rescue => e
		puts "Exception when calling Appwrite::Storage::get_file_preview: #{e}"
	end
end

def get_file_description(client, file_id)
	begin
		storage = Appwrite::Storage.new(client)

		response = storage.get_file(file_id: file_id)
		response
	rescue => e
		puts "Exception when calling Appwrite::Storage::file: #{e}"
	end
end

def backup_to_dropbox(file_descripion, file)
  begin
		dropbox_client = DropboxApi::Client.new(ENV['DROPBOX_API_KEY'])
		dropbox_client.upload("/#{file_descripion['name']}", file)

		puts "#{file_descripion['name']} uploaded successfully!"
  rescue  => e
    puts "Exception when calling DropboxApi::Client::upload: #{e}"
  end
end

data = JSON.parse(ENV['APPWRITE_FUNCTION_EVENT_DATA'])
file_id = data['$id']

client = Appwrite::Client.new()

client
		.set_endpoint(ENV["APPWRITE_ENDPOINT"])
		.set_project(ENV["APPWRITE_PROJECT"])
		.set_key(ENV["APPWRITE_SECRET"])

file_descripion = get_file_description(client, file_id)
file  = get_file(client, file_id)

backup_to_dropbox(file_descripion, file)
