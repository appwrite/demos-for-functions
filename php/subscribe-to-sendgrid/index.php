<?php

include './vendor/autoload.php';

$sendgrid = new SendGrid($_ENV['SENDGRID_API_KEY']);

$email = $argv[1];
$request = [
    "contacts" => [
        [
            "email" => $email
        ]
    ]
];

$reqArrEncoded = json_decode(json_encode($request));

$addContactResponse = $sendgrid->client->marketing()->contacts()->put($reqArrEncoded);
$responseData = json_decode($addContactResponse->body(), true);

if ($addContactResponse->statusCode() >= 200 && $addContactResponse->statusCode() < 300) {
    echo "Successfully subscribed " . $email . " to Sendgrid\n";
}
