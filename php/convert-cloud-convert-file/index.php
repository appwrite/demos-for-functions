<?php

include './vendor/autoload.php';

use Appwrite\Client;
use Appwrite\Services\Storage;
use CloudConvert\CloudConvert;
use \CloudConvert\Models\Job;
use CloudConvert\Models\Task;

$client = new Client();

// Code cleanup
// Readme
// Test with input from function on platform
// change everything to env variables
// PR 🎉

$client
    // ->setProject($_ENV['APPWRITE_FUNCTION_PROJECT_ID']) // Your project ID available automatically
    // ->setEndpoint($_ENV['APPWRITE_ENDPOINT']) // Your API Endpoint
    // ->setKey($_ENV['APPWRITE_API_KEY']) // Your secret API key
    ->setProject('617c1eb1e5656') // Your project ID available automatically
    ->setEndpoint('http://localhost:2222/v1') // Your API Endpoint
    ->setKey('af890e31b9ff0f6d7a3eae81395f19ac50d1872b5cc64000fe0d9e734bbe8da3004b0e5ff97c8ac85546df8e186a128931a3637d7610cd6c8813a1ac63dc2c5cb3444731848e3d21b6c27239d06e365b2067a9f1f75a2c35e45580671ed8b98641649d76ce2a83660402b4479d55619f66cef40c0f12f873b1283a520d344762') // Your secret API key
;

$storage = new Storage($client);

$cloudconvert = new CloudConvert([
    'api_key' => 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiNzIyYWQwMjI4OGU2MThkYmQ5ZjEwNjY0MzQyNjQ1MzBhYWEzMzAxOGIxNTAwMThkMTA2YTRiMjk0Y2Y5ZTlhNzQ1ZTdmZGU0OWM2ODY2MTciLCJpYXQiOjE2MzU1MzIxNTMuMTU1MzA5LCJuYmYiOjE2MzU1MzIxNTMuMTU1MzEzLCJleHAiOjQ3OTEyMDU3NTMuMTMzNTg3LCJzdWIiOiI1NDM1Mjg1NyIsInNjb3BlcyI6WyJ1c2VyLnJlYWQiLCJ1c2VyLndyaXRlIiwidGFzay5yZWFkIiwidGFzay53cml0ZSIsIndlYmhvb2sucmVhZCIsIndlYmhvb2sud3JpdGUiLCJwcmVzZXQucmVhZCIsInByZXNldC53cml0ZSJdfQ.OWH7iIjABXik1pyimmuBKFRE_Iy2O3BovBuUGoV1KrAn3Vtoa5Shnts_bg4SX3etMz5aZZAt829mMkAKFuziGtQ4_HpVCDIdoUbUyCLBwRAAJ1UiT7RFaB6XmSUCnBNUqvInEp0sYkMXmL_rxcGZO8xgb3FVnPQ3To8YtD_7Qp-9odsEXeFJ4AldeElwD-3yX-7VwBVjCfwOcwYIpEce7VPNU2rysPu8xmfaUn1HJjPOp76SKGZgTX1V2iOhCCbbpCMajmN63t1-y9xDHGuARq5TMzquzekHdPrSfQZIgIWNdiOUwf7AdNakdCKsXK_yzJrvnacGhfphbWb4RKcvfvDFL6vDma3M8vhl2v8sQYBQTHC5DXrn3tj1G_62kJXbaarjPG7HwYL57vna3jkZywjNTFMeL1Xv9WLSnUf6FHLzOI_fBij0W9t6gKPpRhfQSGr0mzGmLL2Iij3lHRsPQUs8HrsqfHl3R7OEPk8Zt79iHrEL_tbllwBA9q8hcFl8prcTlgL_4Y4vnu3xwCpXRCNKPcMOR8QwJ1T5lAVkE3kzXajlt1lMCUBA1aCLzU8ec2gwdreoUo4qshS5Hrb0wRNWzDsraLAmrE9SVZ0Y32d0TYl2ePbV0WDTxbBs4nl6GsFaL00SoqZSpV7YKa8pWMjYck1npItcCLFfswqP0go',
    'sandbox' => false
]);

$input = json_decode('{"file_id": "617c4ce134e14", "output_format": "png"}', flags: JSON_THROW_ON_ERROR);

if (!isset($input->file_id) || !isset($input->output_format)) {
    throw new Exception('File ID ("file_id") and output format ("output_format") are required function input, but not provided.');
}

if (!in_array($input->output_format, ['png', 'jpg'])) {
    throw new Exception(sprintf('Only allowed output types are png and jpg (you provided "%s")', $input->output_format));
}

try {
    $fileInfo = $storage->getFile($input->file_id);
    $img = $storage->getFileView($input->file_id);
} catch (Throwable $e) {
    echo sprintf('Unable to find file "%s":\n', $input->file_id);
    throw $e;
}

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
    echo sprintf('Unable to create cloudconvert jobs for file "%s":\n', $fileInfo['name']);
    throw $e;
}

try {
    $uploadTask = $job->getTasks()->whereName('upload-my-file')[0];
    $newFilename = sprintf('%s_converted.%s', pathinfo($fileInfo['name'], PATHINFO_FILENAME), strtolower($input->output_format));
    $cloudconvert->tasks()->upload($uploadTask, $img, $newFilename);
} catch (Throwable $e) {
    echo sprintf('Unable to upload file "%s":\n', $fileInfo['name']);
    throw $e;
}

$cloudconvert->jobs()->wait($job); // Wait for job completion

if ($job->getStatus() !== Job::STATUS_FINISHED) {
    echo sprintf('Unable to finish image conversion job for file "%s":\n', $fileInfo['name']);
}

foreach ($job->getExportUrls() as $file) {

    $source = $cloudconvert->getHttpTransport()->download($file->url)->detach();
    $dest = fopen($file->filename, 'w');

    stream_copy_to_stream($source, $dest);

    try {
        $result = $storage->createFile(
            new \CURLFile(
                $file->filename,
                sprintf('image/%s', strtolower($input->output_format)),
                $file->filename
            )
        );
    } catch (Throwable $e){
        echo sprintf('Unable to create file in storage "%s":\n', $file->filename);
        throw $e;
    }

    unlink($file->filename);
}

