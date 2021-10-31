<?php

include './vendor/autoload.php';

use Appwrite\Client;
use Appwrite\Services\Database;
use Appwrite\Services\Storage;

$client       = new Client();
$client
	->setEndpoint(getenv('APPWRITE_ENDPOINT'))
	->setProject(getenv('APPWRITE_FUNCTION_PROJECT_ID')) //Available by default
	->setKey(getenv('APPWRITE_API_KEY'));
$storage = new Storage($client);
$db      = new Database($client);
$data    = [];
$tempFile = tmpfile();
$collections = $db->listCollections();
foreach ($collections['collections'] as $collection)
{
	$appwriteDocuments = $db->listDocuments($collection['$id']);
	foreach ($appwriteDocuments['documents'] as $document)
	{
		unset($document['$id']);
		unset($document['$collection']);
		unset($document['$permissions']);
		fputcsv($tempFile, $document);
	}
}

$metaData = stream_get_meta_data($tempFile);
$filepath = $metaData['uri'];
$result = $storage->createFile(new \CURLFile($filepath, 'text/csv', 'collections-backup-' . time() . '.csv'));
fclose($tempFile);
