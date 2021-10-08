<?php

include './vendor/autoload.php';
use Appwrite\Client;
use Appwrite\Services\Storage;
use \CloudConvert\CloudConvert;
use \CloudConvert\Models\Job;
use \CloudConvert\Models\Task;
$files = $argv;
unset($files[0]);

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
	$uploadJobs[] = 'import-' . $file;
	$job->addTask(
			(new Task('import/base64', 'import-' . $file))
				->set('file', base64_encode($appwriteFile))
				->set('filename', $file.'.pdf')
	);
}

$job->addTask(
	(new Task('merge', 'merge-1'))
		->set('output_format', 'pdf')
		->set('engine', 'poppler')
		->set('input', $uploadJobs)
);
$job->addTask(
	(new Task('export/url', 'export-1'))
		->set('input', ["merge-1"])
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
		$storage->createFile(new \CURLFile($task->getResult()->files[0]->filename, 'application/pdf', $task->getResult()->files[0]->filename));
		unlink($task->getResult()->files[0]->filename);
	}
}
