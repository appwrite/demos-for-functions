<?php

include './vendor/autoload.php';

use Appwrite\Client;
use GuzzleHttp\Client as GuzzleClient;

$client = new Client();
$client
    ->setEndpoint($_ENV['APPWRITE_ENDPOINT'])
    ->setProject($_ENV['APPWRITE_FUNCTION_PROJECT_ID']) //Available by default
    ->setKey($_ENV['APPWRITE_API_KEY']);

$custom_data = json_decode($_ENV['APPWRITE_FUNCTION_DATA'], true);

// bitly base url
$bitly_api_url = 'https://api-ssl.bitly.com/';

try {
    $guzzleclient = new GuzzleClient([
        // Base URI is used with relative requests
        'base_uri' => $bitly_api_url,
    ]);

    if (empty($custom_data['url'])) throw new Exception('url is requierd!', 500);


    $response = $guzzleclient->request('POST', 'v4/bitlinks', [
        'json' => [
            'long_url' => $custom_data['url']
        ],
        'headers' => [
            'Authorization' => $_ENV['BITLY_ACCESS_TOKEN']
        ],
        'verify' => false,
    ]);

    if (!in_array($response->getStatusCode(), [200, 201]))
        throw new Exception($e->getMessage(), $response->getStatusCode());

    $body = $response->getBody();
    print_r(json_decode($body));
    
} catch (Exception $e) {
    echo $e->getMessage();
}
