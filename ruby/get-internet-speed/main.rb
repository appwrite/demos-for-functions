# Import the helper library
require 'speedtest'

# Starts the speedtest and logs your network details like download and upload speed.
test = Speedtest::Test.new(debug: true)
test.run
