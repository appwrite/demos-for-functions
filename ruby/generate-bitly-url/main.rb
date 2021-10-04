require 'appwrite'
require 'net/http'
require 'uri'

class BitlyUrlShortener
  BITLY_API_ENDPOINT = 'https://api-ssl.bitly.com/v4/shorten'.freeze
  SUCCESS_STATUSES = [200, 201].freeze

  def initialize(access_token)
    @token = access_token
  end

  def call(original_url, domain, group_guid)
    endpoint = URI(BITLY_API_ENDPOINT)
    headers = { 'Content-Type' => 'application/json', 'Authorization' => "Bearer #{@token}" }
    payload = { long_url: original_url, domain: domain, group_guid: group_guid }.compact

    response = Net::HTTP.post(endpoint, payload.to_json, headers)
    result = JSON.parse(response.body)

    unless SUCCESS_STATUSES.include?(response.code.to_i)
      raise Appwrite::Exception.new("#{response.code}: #{result['message']}", response.code, response)
    end

    result['link']
  rescue JSON::ParserError
    raise Appwrite::Exception.new(response.body, response.code, nil)
  end
end

original_url = ENV['APPWRITE_FUNCTION_DATA']
short_url = BitlyUrlShortener.new(ENV['BITLY_ACCESS_TOKEN'])
                             .call(original_url, ENV['BITLY_CUSTOM_DOMAIN'], ENV['BITLY_GROUP_GUID'])

puts short_url
