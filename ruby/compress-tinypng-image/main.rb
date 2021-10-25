require 'tinify'
require 'appwrite'

class CompressTinyPNGImage
  def initialize(api_key)
    Tinify.key = api_key
  end

  def compress(image_url)
    begin
      if image_url
        source = Tinify.from_url(image_url)
				source.to_file(ENV['IMAGE_OUTPUT'] || 'optimized.png')
      else
        puts 'No image url.'
      end
    rescue Tinify::Error => e
      puts e.message
    end
  end
end

def send_image
  client = Appwrite::Client.new()

  client
    .set_endpoint(ENV['APPWRITE_ENDPOINT'])
    .set_project(ENV['APPWRITE_PROJECT'])
    .set_key(ENV['APPWRITE_SECRET'])
    # .setSelfSigned() # Use only on dev mode with a self-signed SSL cert
  ;

  begin
    storage = Appwrite::Storage.new(client);
    file = Appwrite::File.new(ENV['IMAGE_OUTPUT'] || 'optimized.png')
    response = storage.create_file(file: file);
    puts "File created on storage successfully. \n #{response}"
  rescue => exception
    puts error.message
  end
end

CompressTinyPNGImage.new(ENV['TINIFY_API_KEY'])
                    .compress(ENV['IMAGE_URL'])

send_image()



