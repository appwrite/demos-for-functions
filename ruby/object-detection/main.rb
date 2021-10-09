require 'cloudmersive-image-recognition-api-client'
require 'json'
require 'faraday'

def get_file(file_id)
  response = Faraday.new(
                          ENV['APPWRITE_ENDPOINT'],
                          headers: 
                            { 
                              'x-appwrite-project' => ENV['APPWRITE_FUNCTION_PROJECT_ID'],
                              'x-appwrite-key' => ENV['APPWRITE_API_KEY']
                            } 
                        ).get
  response.body
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

file  = get_file(file_id)

object_detection(file)