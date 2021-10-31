<?php

include './vendor/autoload.php';
use Appwrite\Client;
use Appwrite\Services\Storage;
$files = explode(' ', getenv('APPWRITE_FUNCTION_DATA'));

if(empty($files)){
	throw new Exception('Please enter file ids.');
}
$client = new Client();
$client
	->setEndpoint(getenv('APPWRITE_ENDPOINT'))
	->setProject(getenv('APPWRITE_FUNCTION_PROJECT_ID')) //Available by default
	->setKey(getenv('APPWRITE_API_KEY'));
$storage = new Storage($client);
$dropboxClient = new Spatie\Dropbox\Client(getenv('DROPBOX_ACCESS_TOKEN'));
$res = $dropboxClient->listFolder('/', true);
foreach ($files as $i => $file) {
	$appwriteFile = $storage->getFileDownload($file);
	$fileInfo = $storage->getFile($file);
	$dropboxClient->upload('/' . $fileInfo['name'], $appwriteFile, $mode='add', true);
}
