require 'lib/gems/appwrite-1.0.11/lib/appwrite'

client = Appwrite::Client.new()

client
    .set_endpoint('http://localhost/v1') # Your API Endpoint
    .set_project('5fca866c65afc') # Your project ID
    .set_key('8d84bc37d4c59bb3b3e9d2fe67d32f9c359a9198284ecbd193c23d1c5ef667c37aef9d97dbe400e162a3210405622fdf10c8c8ec7419c9af10cf72ad3bc0e3f216efed4c12e0eb1261fb422a45ee1cfe267f6953fd4ed23326ca9143c329a236b433ee460ad0b696037a65ca6c817535f62c4d719c46aba9f1ab664e94e6df53') # Your secret API key
;

storage = Appwrite::Storage.new(client);

response = storage.get_file(file_id: '[FILE_ID]');

puts response