<?php

include './vendor/autoload.php';
use Appwrite\Client;
use Appwrite\Services\Storage;


$client = new Client();
$client
    ->setEndpoint($_ENV['APPWRITE_ENDPOINT'])
    ->setProject($_ENV['APPWRITE_FUNCTION_PROJECT_ID']) //Available by default
    ->setKey($_ENV['APPWRITE_API_KEY'])
;

$storage = new Storage($client);
$result = $storage->listFiles($orderType = 'DESC', $limit = 100);

$timestamp = date_create()->modify("-{$_ENV['DAYS_TO_EXPIRE']} days")->getTimestamp();

$deletedFiles = 0;
foreach ($result['files'] as $file) {
    if ($file['dateCreated'] < $timestamp)
    {
        $storage->deleteFile($file['$id']);
        $deletedFiles++;
        print_r("Deleted {$file['$id']}");
    }
}

print_r("Total files deleted: {$deletedFiles}");