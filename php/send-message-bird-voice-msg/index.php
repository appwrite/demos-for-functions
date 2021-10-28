<?php

include './vendor/autoload.php';

$MessageBird = new \MessageBird\Client($_ENV['MESSAGE_BIRD_ACCESS_KEY']);
$VoiceMessage = new \MessageBird\Objects\VoiceMessage();

$payload = json_decode($_ENV['APPWRITE_FUNCTION_DATA'], true);

$VoiceMessage->recipients = [ $payload['phoneNumber'] ];
$VoiceMessage->body = $payload['text'];
$VoiceMessageResult = $MessageBird->voicemessages->create($VoiceMessage);

$status = $VoiceMessageResult->recipients->items[0]->status;

echo "status: {$status}";
