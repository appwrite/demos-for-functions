# Import the helper libraries
require "twilio-ruby"
require "json"

# Set the environment variables.
account_sid = ENV["TWILIO_ACCOUNT_SID"]
auth_token = ENV["TWILIO_AUTH_TOKEN"]

# Get the sender's and receiver's mobile number and the message which has to be send.
payload = JSON.parse(ENV["APPWRITE_FUNCTION_DATA"])
sender=payload["sender"]
receiver=payload["receiver"]
message=payload["message"]

# Validation to check if all data were provided
if !receiver || !sender || !message
    raise "Invalid data provided"
end

# Send your message
begin
    @client = Twilio::REST::Client.new account_sid, auth_token
    message = @client.messages.create(
        body: message,
        from: sender,
        to: receiver
        )
    puts message 
rescue Twilio::REST::TwilioError => e
    puts e.message
end