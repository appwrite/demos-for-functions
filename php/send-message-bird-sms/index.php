<?php

   require_once 'vendor/autoload.php' ;
   use Appwrite\Client;
   
   
   $client = new Client();
   $client
    ->setEndpoint($_ENV['API_END_POINT']) // Your API Endpoint
    ->setProject($_ENV['APPWRITE_FUNCTION_PROJECT_ID']) // Your project ID available automatically
    ->setKey($_ENV['APPWRITE_API_KEY']) // Your secret API key
    ;

    $messagebird = new MessageBird\Client($_ENV['MESSAGE_BIRD_KEY']);
    $message = new MessageBird\Objects\Message;

    $message->originator = $_ENV['ORIGINATOR'];
    $message->recipients = $_ENV['RECIPIENT'];;
    $message->body = $_ENV['MSG'];

    $response = $messagebird->messages->create($message);
    var_dump($response);

?>