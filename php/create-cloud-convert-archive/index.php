<?php

include './vendor/autoload.php';
use Appwrite\Client;
use Appwrite\Services\Storage;
use \CloudConvert\CloudConvert;
use \CloudConvert\Models\Job;
use \CloudConvert\Models\Task;
$files = explode(' ', getenv('APPWRITE_FUNCTION_DATA'));

if(empty($files)){
	throw new Exception('Please enter file ids.');
}
$client = new Client();
$client
	->setEndpoint(getenv('APPWRITE_ENDPOINT'))
	->setProject(getenv('APPWRITE_FUNCTION_PROJECT_ID')) //Available by default
	->setKey(getenv('APPWRITE_API_KEY'));

$cloudconvert = new CloudConvert([
	'api_key' => getenv('CC_API_KEY'),
	'sandbox' => false
]);
$storage = new Storage($client);
$uploadJobs = [];
$job = new Job();
foreach ($files as $i => $file) {
	$appwriteFile = $storage->getFileDownload($file);
	$fileInfo = $storage->getFile($file);
	$uploadJobs[] = 'import-' . $file;
	$job->addTask(
		(new Task('import/base64', 'import-' . $file))
			->set('file', base64_encode($appwriteFile))
			->set('filename', $fileInfo['name'])
	);
}

$job->addTask(
	(new Task('archive', 'archive-1'))
		->set('output_format', 'zip')
		->set('engine', 'archivetool')
		->set('input', $uploadJobs)
);
$job->addTask(
	(new Task('export/url', 'export-1'))
		->set('input', ["archive-1"])
		->set('inline', false)
		->set('archive_multiple_files', false)
);
$cloudconvert->jobs()->create($job);
while($job->getStatus() != 'finished')
{
	$job = $cloudconvert->jobs()->get($job->getId());
	sleep(1);
}
foreach ($job->getTasks() as $task) {
	if ($task->getName() == 'export-1') {
		file_put_contents($task->getResult()->files[0]->filename, file_get_contents($task->getResult()->files[0]->url));
		$res = $storage->createFile(new \CURLFile($task->getResult()->files[0]->filename, 'application/zip', $task->getResult()->files[0]->filename));
		unlink($task->getResult()->files[0]->filename);
		echo $res['$id'];
	}
}
