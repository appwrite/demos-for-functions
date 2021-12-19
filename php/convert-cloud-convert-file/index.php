<?php

include './vendor/autoload.php';

use Appwrite\Client;
use Appwrite\Services\Storage;
use CloudConvert\CloudConvert;
use CloudConvert\Models\Job;
use CloudConvert\Models\Task;

// Validate function input
$input = json_decode($_ENV['APPWRITE_FUNCTION_DATA'], flags: JSON_THROW_ON_ERROR);

if (!isset($input->file_id) || !isset($input->output_format)) {
    throw new Exception('File ID ("file_id") and output format ("output_format") are required function input, but not provided.');
}

if (!in_array($input->output_format, ['png', 'jpg'])) {
    throw new Exception(
        sprintf('Only allowed output types are png and jpg (you provided "%s")', $input->output_format)
    );
}

// Create API clients
$client = new Client();

$client
    ->setProject($_ENV['APPWRITE_FUNCTION_PROJECT_ID']) // Your project ID available automatically
    ->setEndpoint($_ENV['APPWRITE_ENDPOINT']) // Your API Endpoint
    ->setKey($_ENV['APPWRITE_API_KEY']) // Your secret API key
;

$storage = new Storage($client);

$cloudconvert = new CloudConvert([
    'api_key' => $_ENV['CLOUDCONVERT_API_KEY'],
    'sandbox' => false
]);

// Fetch file information
try {
    $fileInfo = $storage->getFile($input->file_id);
    $img = $storage->getFileView($input->file_id);
} catch (Throwable $e) {
    echo sprintf("Unable to find file \"%s\":\n", $input->file_id);
    throw $e;
}

// Register a CloudConvert job
try {
    $job = (new Job())
        ->addTask(new Task('import/upload', 'upload-my-file'))
        ->addTask(
            (new Task('convert', 'convert-my-file'))
                ->set('input', 'upload-my-file')
                ->set('output_format', strtolower($input->output_format))
        )
        ->addTask(
            (new Task('export/url', 'export-my-file'))
                ->set('input', 'convert-my-file')
        );
    $cloudconvert->jobs()->create($job);
} catch (Throwable $e) {
    echo sprintf("Unable to create cloudconvert jobs for file \"%s\":\n", $fileInfo['name']);
    throw $e;
}

// Upload the file to CloudConvert
try {
    $newFilename = sprintf(
        '%s_converted.%s',
        pathinfo($fileInfo['name'], PATHINFO_FILENAME),
        strtolower($input->output_format)
    );
    $uploadTask = $job->getTasks()->whereName('upload-my-file')[0];
    $cloudconvert->tasks()->upload($uploadTask, $img, $newFilename);
} catch (Throwable $e) {
    echo sprintf("Unable to upload file \"%s\":\n", $fileInfo['name']);
    throw $e;
}

// Wait for the conversion to finish
$cloudconvert->jobs()->wait($job);

if ($job->getStatus() !== Job::STATUS_FINISHED) {
    echo sprintf("Unable to finish image conversion job for file \"%s\":\n", $fileInfo['name']);
}

foreach ($job->getExportUrls() as $file) {

    // Get the converted file from CloudConvert...
    try {
        $source = $cloudconvert->getHttpTransport()->download($file->url)->detach();
        $dest = fopen($file->filename, 'w');

        stream_copy_to_stream($source, $dest);
    } catch (Throwable $e) {
        echo sprintf("Unable to download converted file \"%s\":\n", $file->filename);
        throw $e;
    }

    // ... and upload it to Appwrite Storage
    try {
        $result = $storage->createFile(
            new \CURLFile(
                $file->filename,
                sprintf('image/%s', strtolower($input->output_format)),
                $file->filename
            )
        );
    } catch (Throwable $e) {
        echo sprintf("Unable to create file in storage \"%s\":\n", $file->filename);
        throw $e;
    } finally {
        unlink($file->filename);
    }

    echo sprintf(
        "Successfully converted file \"%s\" to \"%s\" (format %s)\n",
        $fileInfo['name'],
        $file->filename,
        strtolower($input->output_format)
    );
}

