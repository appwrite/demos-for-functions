require 'appwrite'
require 'cloudmersive-image-recognition-api-client'

def get_file(client, file_id)
	begin
		storage = Appwrite::Storage.new(client)

		response = storage.get_file_preview(file_id: file_id)
		response
	rescue => e
		puts "Exception when calling Appwrite::Storage::get_file_preview: #{e}"
	end
end

def object_detection(file)
	CloudmersiveImageRecognitionApiClient.configure do |config|
		config.api_key['Apikey'] = ENV['CLOUDMERSIVE_API_KEY']
	end

	api_instance = CloudmersiveImageRecognitionApiClient::RecognizeApi.new

  begin
    #Detect objects including types and locations in an image
    result = api_instance.recognize_detect_objects(file)
    puts result
  rescue CloudmersiveImageRecognitionApiClient::ApiError => e
    puts "Exception when calling RecognizeApi->recognize_detect_objects: #{e}"
  end
end

data = JSON.parse(ENV['APPWRITE_FUNCTION_EVENT_DATA'])
file_id = data['$id']

client = Appwrite::Client.new()

client
		.set_endpoint(ENV["APPWRITE_ENDPOINT"])
		.set_project(ENV["APPWRITE_PROJECT"])
		.set_key(ENV["APPWRITE_SECRET"])

file  = get_file(client, file_id)
object_detection(file)
