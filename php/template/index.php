<?php

include './vendor/autoload.php';
use Appwrite\Client;
use Appwrite\Services\Storage;


$client = new Client();
$client
    ->setEndpoint('http://[HOSTNAME_OR_IP]/v1') // Your API Endpoint
    ->setProject($_ENV['APPWRITE_FUNCTION_PROJECT_ID']) // Your project ID available automatically
    ->setKey('8d84bc37d4c59bb3b3......4e6df53') // Your secret API key
;

$storage = new Storage($client);
$result = $storage->getFile('5fd631dff3d8b');
print_r($result);