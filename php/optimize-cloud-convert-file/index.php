<?php

include './vendor/autoload.php';

use Appwrite\Client;
use Appwrite\Services\Storage;
use \CloudConvert\CloudConvert;
use \CloudConvert\Models\Job;
use \CloudConvert\Models\Task;

$fileId = getenv('APPWRITE_FUNCTION_DATA');
if(empty($fileId)){
    throw new Exception('Please enter a file ID');
}
$client = new Client();
$client 
    ->setEndpoint(getenv('APPWRITE_ENDPOINT'))
    ->setProject(getenv('APPWRITE_FUNCTION_PROJECT_ID'))
    ->setKey(getenv('APPWRITE_API_KEY'));
    
$storage = new Storage($client);

$fileToOptimize = $storage->getFileView($fileId);
$fileProp = $storage->getFile($fileId);

$cloudConvert = new CloudConvert([
    'api_key' => getenv('CLOUDCONVERT_API_KEY'),
    'sandbox' => false
]);

$job = (new Job())
    ->setTag('appwrite-optimize-img')
    ->addTask(new Task('import/upload', 'upload-file'))
    ->addTask(
        (new Task('optimize', 'optimize-image'))
            ->set('input', 'upload-file')
    )
    ->addTask(
        (new Task('export/url', 'export-file'))
            ->set('input', 'optimize-image')
    )
;

$cloudConvert->jobs()->create($job);

$uploadTask = $job->getTasks()->whereName('upload-file')[0];

$cloudConvert->tasks()->upload($uploadTask, $fileToOptimize, $fileProp['name']);

$cloudConvert->jobs()->wait($job);

foreach($job->getTasks() as $task) {
    if ($task->getName() == 'export-file') {
        $file = file_get_contents($task->getResult()->files[0]->url);
        file_put_contents($task->getResult()->files[0]->filename, $file);        

        $response = $storage->createFile(new \CURLFile($task->getResult()->files[0]->filename, $fileProp['mimeType'], $task->getResult()->files[0]->filename));
        unlink($task->getResult()->files[0]->filename);

        print_r("File ID:" . $response['$id']);
    }
}
