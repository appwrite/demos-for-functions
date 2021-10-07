<?php
try
{
    $strApiKey = $_ENV['GIPHY_API_KEY'] ?? null;
    $strQuery = trim($_ENV['APPWRITE_FUNCTION_DATA']) != '' ? $_ENV['APPWRITE_FUNCTION_DATA'] : ($_ENV['GIPHY_QUERY'] ?? null);

    if(!$strQuery)
        throw new Exception('Please provide a search query.');
    elseif(!$strApiKey)
        throw new Exception('ENV variable "GIPHY_API_KEY" not set.');

    $oResults = json_decode(get('https://api.giphy.com/v1/gifs/search?'. http_build_query([
        'api_key' => $strApiKey,
        'q' => $strQuery
    ])));

    if(!isset($oResults->data))
        echo 'Something went wrong..';
    elseif(empty($oResults->data))
        echo sprintf('No results found for query: "%s".', $strQuery);
    else
        echo $oResults->data[0]->url;
}
catch(Exception $e)
{
    echo $e->getMessage();
}

function get($strUrl)
{
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $strUrl);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
    $response = curl_exec($ch);
    curl_close($ch);
    return $response;        
}
?>