<?php

include './vendor/autoload.php';
use Mailgun\Mailgun; // Import the mailgun sdk

// get mailgun environment variables
$domain = $_ENV['MAILGUN_DOMAIN'];
$apiKey = $_ENV['MAILGUN_API_KEY'];

// initialize mailgun sdk with api key
$mg = Mailgun::create($apiKey);

// Get the name and email of the newly created user from Appwrite's environment variable
$payload = json_decode($_ENV['APPWRITE_FUNCTION_EVENT_DATA']); 
$name = $payload->name;
$email = $payload->email; 

// Now, compose and send the message
$mg->messages()->send($domain, [
  'from'    => 'Welcome to My Awesome App <welcome@my-awesome-app.io>',
  'to'      => $email,
  'subject' => 'Welcome on board '.$name,
  'text'    => 'Hi '.$name.' \nGreat to have you with us. ! 😍'
]);