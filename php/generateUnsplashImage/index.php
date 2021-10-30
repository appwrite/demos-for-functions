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
    $output = ["imageAuthor" => $response->getResults()[0]["user"]["name"],"imageUrl" => $response->getResults()[0]["urls"]["raw"]];
    echo json_encode($output, JSON_UNESCAPED_SLASHES);
}
else{
    echo "No result found for the keyword entered";
}
