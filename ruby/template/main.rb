require 'appwrite'

client = Appwrite::Client.new()

client
    .set_endpoint('http://[HOSTNAME_OR_IP]/v1') # Your API Endpoint
    .set_project(ENV.APPWRITE_FUNCTION_PROJECT_ID) # Your project ID available by default
    .set_key('8d84bc37d4c59bb3b3e9d...a9f1ab664e94e6df53') # Your secret API key
;

storage = Appwrite::Storage.new(client);

response = storage.get_file(file_id: '[FILE_ID]');

puts response