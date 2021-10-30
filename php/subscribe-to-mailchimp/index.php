<?php

include './vendor/autoload.php';

// Endpoint API URL
$api_endpoint = 'https://<us>.api.mailchimp.com/3.0/';

// Explode API Key and replace api endpoint
list(, $data_center) = explode('-', $_ENV['APPWRITE_MAILCHIMP_API_KEY']);
$api_endpoint = str_replace('<us>', $data_center, $api_endpoint);

// get custom data and check if email is exist 
$custom_data = json_decode($_ENV['APPWRITE_FUNCTION_DATA'], true);
if (empty($custom_data['email'])) return print_r('E-Mail is required');

// Create json 
$json = json_encode([
    'email_address' => $custom_data['email'],
    'status'        => 'subscribed', //pass 'subscribed' or 'pending'
]);


// Try to send request to mailchimp api
try {
    $ch = curl_init($api_endpoint . 'lists/' . $_ENV['APPWRITE_MAILCHIMP_LIST_ID'] . '/members');
    curl_setopt($ch, CURLOPT_USERPWD, 'user:' . $_ENV['APPWRITE_MAILCHIMP_API_KEY']);
    curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_TIMEOUT, 10);
    curl_setopt($ch, CURLOPT_POST, 1);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $json);
    $result = curl_exec($ch);
    $status_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    // Response
    print_r($result);
} catch (Exception $e) {
    echo $e->getMessage();
}
