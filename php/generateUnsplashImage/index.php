<?php

include './vendor/autoload.php';

$keyword = getenv('APPWRITE_FUNCTION_DATA');
if (empty($keyword)) {
    throw new Exception('Please enter a keyword to search.');
};
Unsplash\HttpClient::init([
    'applicationId'    => getenv('UNSPLASH_API_KEY'),
    'utmSource' => 'Appwrite Function'
]);
$response = Unsplash\Search::photos($keyword);
if(!empty($response->getResults())){
    print_r("Image url:{$response->getResults()[0]["urls"]["raw"]} and the Author Name is:{$response->getResults()[0]["user"]["name"]}");
}
else{
    print_r("No result found for the keyword entered");
}
