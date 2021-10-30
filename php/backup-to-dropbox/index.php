<?php

include './vendor/autoload.php';
use Appwrite\Client;
use Appwrite\Services\Database;

$client = new Client();
$client->setSelfSigned()
    ->setEndpoint(getenv('APPWRITE_ENDPOINT'))
    ->setProject(getenv('APPWRITE_FUNCTION_PROJECT_ID'))
    ->setKey(getenv('APPWRITE_API_KEY'));

$database = new Database($client);
$collections = $database->listCollections()['collections'];
foreach ( $collections as $collection ){
    $collectionCsv = str_putcsv($collection,$database);
    uploadData($collectionCsv);
}

echo 'Done Uploading to DropBox';

/**
 * Convert a collection of documents to csv
 * @param  array $collection 
 * @param  \Appwrite\Services\Database $database
 * @return string CSV text
 */
function str_putcsv($collection, $database) {
    # Generate CSV data from array
    $fh = fopen('php://temp', 'rw'); # in-memory csv
    $documents =  $database->listDocuments($collection['$id'])['documents'];
    $documentKeys = array_keys(current($documents));
    # write out the headers
    fputcsv($fh, $documentKeys);
    # write out the data
    foreach ( $documents as $document ) {
            $result = [];
            foreach($document as $attr){
                if(is_array($attr)){
                    array_push($result,json_encode($attr));
                }else{
                    array_push($result,$attr);
                } 
            }
            fputcsv($fh, $result);
    }
    rewind($fh);
    $csv = stream_get_contents($fh);
    fclose($fh);
    return $csv;
}

function uploadData($collectionCsv){
    $authorizationToken = getenv('DROPBOX_ACCESS_TOKEN');
$url = 'https://content.dropboxapi.com/2/files/upload';
$additional_headers = array(                                                                          
   "Authorization: Bearer ${authorizationToken}",
   'Dropbox-API-Arg: {"path": "/backup/collection.csv","mode": "add","autorename": true,"mute": false,"strict_conflict": false}',
   'Content-Type: application/octet-stream'
);
$ch = curl_init($url);                                                                      
curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "POST");                                                                     
curl_setopt($ch, CURLOPT_POSTFIELDS, $collectionCsv);                                                                  
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);                                                                      
curl_setopt($ch, CURLOPT_HTTPHEADER, $additional_headers); 
curl_exec ($ch);
}
