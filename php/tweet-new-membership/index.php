<?php
	include './vendor/autoload.php';
	require_once './config.php';

	use Appwrite\Client;

	$client = new Client();

	$client
		->setEndpoint($_ENV['APPWRITE_API_ENDPOINT']) // Your API Endpoint
		->setProject($_ENV['APPWRITE_PROJECT_ID']) // Your project ID
		->setKey($_ENV['APPWRITE_API_KEY_SECRET']) // Your secret API key
	;
	
	// For more information about these functions: https://stackoverflow.com/a/12939923
    function buildBaseString($baseURI, $method, $params) {
        $r = array();
        ksort($params);
        foreach($params as $key=>$value){
            $r[] = "$key=" . rawurlencode($value);
        }
        return $method."&" . rawurlencode($baseURI) . '&' . rawurlencode(implode('&', $r));
    }

    function buildAuthorizationHeader($oauth) {
        $r = 'Authorization: OAuth ';
        $values = array();
        foreach($oauth as $key=>$value)
            $values[] = "$key=\"" . rawurlencode($value) . "\"";
        $r .= implode(', ', $values);
        return $r;
    }

	// Twitter API v1.1
    $url = "https://api.twitter.com/1.1/statuses/update.json";
    $oauth = array( 'oauth_consumer_key' => $_ENV['TWITTER_CONSUMER_KEY'],
                    'oauth_nonce' => time(),
                    'oauth_signature_method' => 'HMAC-SHA1',
                    'oauth_token' => $_ENV['TWITTER_ACCESS_TOKEN'],
                    'oauth_timestamp' => time(),
                    'oauth_version' => '1.0');

    $base_info = buildBaseString($url, 'POST', $oauth);
    $composite_key = rawurlencode($_ENV['TWITTER_CONSUMER_SECRET']) . '&' . rawurlencode($_ENV['TWITTER_ACCESS_TOKEN_SECRET']);
    $oauth_signature = base64_encode(hash_hmac('sha1', $base_info, $composite_key, true));
    $oauth['oauth_signature'] = $oauth_signature;

	$postfields = array("status"=> "A new user has joined the team! Welcome aboard!");

    // Make requests
    $header = array(buildAuthorizationHeader($oauth), 'Expect:');
    $options = array( CURLOPT_HTTPHEADER => $header,
                      CURLOPT_POSTFIELDS => $postfields,
                      CURLOPT_HEADER => false,
                      CURLOPT_URL => $url);

    $feed = curl_init();
    curl_setopt_array($feed, $options);
    $json = curl_exec($feed);
    curl_close($feed);

    $twitter_data = json_decode($json);

	// Printing response from twitter
	print_r ($twitter_data);

?>