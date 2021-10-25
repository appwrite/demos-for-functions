require 'json'
require 'down'
require 'appwrite'


# See https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#Ruby for more details
def get_tile_number(lat_deg, lng_deg, zoom)
  lat_rad = lat_deg/180 * Math::PI
  n = 2.0 ** zoom
  x = ((lng_deg + 180.0) / 360.0 * n).to_i
  y = ((1.0 - Math::log(Math::tan(lat_rad) + (1 / Math::cos(lat_rad))) / Math::PI) / 2.0 * n).to_i

  {:x => x, :y => y}
end

# Fetch the map preview image from Open Street Map tile server
def fetch_map_preview(latitude, longitude, zoom)
  tile = get_tile_number(latitude, longitude, zoom)
  api_url = "http://tile.openstreetmap.org/#{zoom}/#{tile[:x]}/#{tile[:y]}.png"
  file_name = "#{latitude}_#{longitude}_#{zoom}.png"
  File.new(file_name, File::CREAT)
  Down::NetHttp.download(api_url, destination: "./#{file_name}")

  file_name
end

# Save file to Appwrite storage
def save_image(file)
  client = Appwrite::Client.new()

  client
    .set_endpoint(ENV['APPWRITE_ENDPOINT']) # Your API Endpoint
    .set_project(ENV['APPWRITE_FUNCTION_PROJECT_ID']) # Your project ID available by default
    .set_key(ENV['APPWRITE_API_KEY']) # Your secret API key

  storage = Appwrite::Storage.new(client)

  response = storage.create_file(file: Appwrite::File.new(file));

  puts response
end

data = JSON.parse(ENV['APPWRITE_FUNCTION_DATA'])
map_preview_image = fetch_map_preview(data["latitude"], data["longitude"], data["zoom_level"])
save_image(map_preview_image)
File.delete(map_preview_image)