<?php

// Autoload
include __DIR__ . '/vendor/autoload.php';

use Appwrite\Client;
use MessageBird\Objects\Conversation\Content;
use MessageBird\Objects\Conversation\SendMessage;


// Set up client
$client = new Client();
$client->setEndpoint($_ENV['APPWRITE_ENDPOINT'])
       ->setProject($_ENV['APPWRITE_FUNCTION_PROJECT_ID'])
       ->setKey($_ENV['APPWRITE_API_KEY']);

$params = json_decode($_ENV['APPWRITE_FUNCTION_DATA'], true);

// Initialise MessageBird client
$messageBird = new MessageBird\Client($params['MESSAGE_BIRD_ACCESS_KEY']);

// Set up the message to send
$message = (new SendMessage())->loadFromArray(
    [
        'from'      => $params['WHATSAPP_CHANNEL_ID'],
        'to'        => $params['WHATSAPP_TO'],
        'type'      => 'text',
        'content'   => (new Content)->loadFromArray(['text' => $params['TEXT']]),
        'reportUrl' => $params['REPORT_URL'],
    ]
);

try
{
    $response = $messageBird->conversationSend->send($message);
    print_r($response);
}
catch (Exception $e)
{
    print_r("Exception: " . $e->getMessage());
}
