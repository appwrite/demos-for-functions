# Import the helper library
require "speedtest_net"

# Starts the speedtest 
result = SpeedtestNet.run    

puts "Download speed is: #{result.pretty_download}"       # download bit/second in speedtest
puts "Upload speed is: #{result.pretty_upload}"           # upload bit/second in speedtest
