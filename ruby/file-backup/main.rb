require 'json'
require 'faraday'
require 'dotenv/load'
require 'dropbox_api'

def get_file(file_id)
	begin
		response = Faraday.new(
														url: "#{ENV['APPWRITE_ENDPOINT']}/storage/files/#{file_id}/download",
														headers: 
															{ 
																'x-appwrite-project' => ENV['APPWRITE_FUNCTION_PROJECT_ID'],
																'x-appwrite-key' => ENV['APPWRITE_API_KEY']
															} 
													).get
		response.body	
	rescue => e
		puts "Exception when calling Appwrite::Storage::download_file: #{e}"
	end
end

def get_file_description(file_id)
	begin
		response = Faraday.new(
														url: "#{ENV['APPWRITE_ENDPOINT']}/storage/files/#{file_id}",
														headers: 
															{ 
																'x-appwrite-project' => ENV['APPWRITE_FUNCTION_PROJECT_ID'],
																'x-appwrite-key' => ENV['APPWRITE_API_KEY']
															} 
													).get
		response.body	
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
    puts "Exception when calling DropboxApi::Client::upload_by_chunks: #{e}"
  end
end

data = JSON.parse(ENV['APPWRITE_FUNCTION_EVENT_DATA'])
file_id = data['$id']

file  = get_file(file_id)
file_descripion = JSON.parse(get_file_description(file_id))

backup_to_dropbox(file_descripion, file)
