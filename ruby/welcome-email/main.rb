require 'json'
require 'net/http'

# Parsing data
data = JSON.parse(ENV['APPWRITE_FUNCTION_EVENT_DATA'])
# puts data
name = data["name"]
email = data["email"]

# Using Mailgun API to send email
uri = URI('https://api.mailgun.net/v3/%{a}/messages' % { a: ENV['MAILGUN_DOMAIN'] })
req = Net::HTTP::Post.new(uri)

# Basic Authentication 
req.basic_auth 'auth', '%{b}' % { b:[ENV['MAILGUN_API_KEY']] }

# Setting up form data
req.set_form_data('from' => 'Excited User <mailgun@%{c}>' % { c: ENV['MAILGUN_DOMAIN'] }, 'to' => email, 'subject': 'Hello, %{d}' % { d: name }, 'text' => 'Testing some Mailgun awesomness!')

# Printing response code
res = Net::HTTP.get_response(uri)
puts 'response code: %{e}' % { e:res.code.to_s }
