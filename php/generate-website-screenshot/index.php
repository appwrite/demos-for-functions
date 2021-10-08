<?php

include './vendor/autoload.php';

use Appwrite\Client;
use Appwrite\Services\Storage;
use \CloudConvert\CloudConvert;
use \CloudConvert\Models\Job;
use \CloudConvert\Models\Task;

$url = getenv('APPWRITE_SCREENSHOT_URL');
$client = new Client();
$client
    ->setEndpoint(getenv('APPWRITE_ENDPOINT'))
    ->setProject(getenv('APPWRITE_FUNCTION_PROJECT_ID'))
    ->setKey(getenv('APPWRITE_API_KEY'));

$cloudconvert = new CloudConvert([
    'api_key' => getenv('CLOUDCONVERT_API_KEY'),
    'sandbox' => false
]);
$storage = new Storage($client);
$job = captureScreenshot($url);
$cloudconvert->jobs()->create($job);

while ($job->getStatus() != 'finished') {
    $job = $cloudconvert->jobs()->get($job->getId());
    sleep(1);
}
storeFile($storage ,$job);

function captureScreenshot($url)
{
    $job = new Job();
    $job->addTask(
        (new Task('capture-website', 'capture-url'))
            ->set('url', $url)
            ->set('output_format', 'jpg')
    );
    $job->addTask(
        (new Task('export/url', 'export-my-file'))
            ->set('input', 'capture-url')
    );
    return $job;
}
function storeFile($storage, $job)
{
    foreach ($job->getTasks() as $task) {
        if ($task->getName() == 'export-my-file') {
            file_put_contents($task->getResult()->files[0]->filename, file_get_contents($task->getResult()->files[0]->url));
            $response = $storage->createFile(new \CURLFile($task->getResult()->files[0]->filename, 'image/jpeg', $task->getResult()->files[0]->filename));
            unlink($task->getResult()->files[0]->filename);
            echo "File ID:" . $response['$id'];
        }
    }
}
