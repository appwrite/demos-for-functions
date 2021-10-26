<?php

// Autoload
include __DIR__ . '/vendor/autoload.php';

use GuzzleHttp\Client;
use GuzzleHttp\Exception\GuzzleException;
use GuzzleHttp\RequestOptions;
use GuzzleHttp\TransferStats;

$params       = json_decode($_ENV['APPWRITE_FUNCTION_DATA'], true);
$functionId   = $_ENV['APPWRITE_FUNCTION_ID'];
$functionName = $_ENV['APPWRITE_FUNCTION_NAME'];

if (empty($params['url']))
{
    fwrite(STDERR, "Error: The target URL must be present in function data.");
    exit(1);
}

$httpClient = new Client();

$url                = $params['url'];
$method             = $params['method'] ?? 'POST';
$headers            = $params['headers'] ?? [];
$requestContentType = $headers['Content-Type'] ?? 'text/plain';
$requestBody        = $requestContentType === 'application/json'
    ? json_encode($params['body'] ?? '')
    : $params['body'] ?? '';

$capture = function (TransferStats $stats) use (&$effectiveUri) {
    $effectiveUri = $stats->getEffectiveUri();
};

try
{
    $response = $httpClient->request(
        $method,
        $url,
        [
            RequestOptions::HEADERS  => $headers
                + [
                    'User-Agent'               => 'Appwrite-Server',
                    'X-Appwrite-Function-UID'  => $functionId,
                    'X-Appwrite-Function-Name' => $functionName,
                ],
            RequestOptions::BODY     => $requestBody,
            RequestOptions::ON_STATS => function (TransferStats $stats) use (&$effectiveUri): void {
                $effectiveUri = $stats->getEffectiveUri();
            },
        ]
    );
}
catch (GuzzleException $e)
{
    fwrite(STDERR, 'Exception: ' . $e->getMessage());
    exit(2);
}

$responseBody = $response->getHeaderLine('content-type') === 'application/json'
    ? json_encode($response->getBody())
    : $response->getBody();

$result = json_encode
(
    [
        'success' => (bool) preg_match('/^2\d{2}$/', $response->getStatusCode()),
        'url'     => $effectiveUri,
        'status'  => $response->getStatusCode() . ' ' . $response->getReasonPhrase(),
        'headers' => $response->getHeaders(),
        'body'    => $responseBody,
    ]
);

print_r($result);
