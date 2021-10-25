require "uri"
require "net/http"
require 'json'

payload = JSON.parse(ENV["APPWRITE_FUNCTION_DATA"])
recipient = payload["phoneNumber"]
body = payload["text"];
url = URI("https://rest.messagebird.com/voicemessages")
access_key = ENV["MESSAGE_BIRD_ACCESS_KEY"]

https = Net::HTTP.new(url.host, url.port)
https.use_ssl = true
request = Net::HTTP::Post.new(url)
request["Authorization"] = "AccessKey #{access_key}"
request["Content-Type"] = "application/x-www-form-urlencoded"
request.set_form_data('recipients' => [recipient], 'body' => body)

begin
    response = https.request(request)
    response_json = JSON.parse(response.read_body)
    puts response_json["recipients"]["items"][0]["status"]
rescue Exception => ex
    p "Exception occurred: " + ex.message
    p ex.backtrace
end

