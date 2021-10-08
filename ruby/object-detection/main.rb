require 'cloudmersive-image-recognition-api-client'
require 'json'
require 'appwrite'

def get_file(file_id)
  client = Appwrite::Client.new()

  client
    .set_endpoint(ENV['APPWRITE_ENDPOINT'])
    .set_project(ENV['APPWRITE_FUNCTION_PROJECT_ID'])
    .set_key(ENV['APPWRITE_API_KEY'])
  ;

  begin
    storage = Appwrite::Storage.new(client)
    return storage.get_file(file_id: file_id)
  rescue => exception
    puts error.message
  end
end

def object_detection(image_file)
	CloudmersiveImageRecognitionApiClient.configure do |config|
		config.api_key['Apikey'] = ENV['CLOUDMERSIVE_API_KEY']
	end

	api_instance = CloudmersiveImageRecognitionApiClient::RecognizeApi.new

	begin
		result = api_instance.recognize_detect_objects(image_file)
		puts result
	rescue CloudmersiveImageRecognitionApiClient::ApiError => e
		puts "Exception when calling RecognizeApi->recognize_detect_objects: #{e}"
	end
end

data = JSON.parse(ENV['APPWRITE_FUNCTION_EVENT_DATA'])
file_id = data['$id']

object_detection(get_file(file_id))