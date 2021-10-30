<?php
try
{
    $strCountryCode = trim($_ENV['APPWRITE_FUNCTION_DATA']) != '' ? $_ENV['APPWRITE_FUNCTION_DATA'] : ($_ENV['COUNTRY_CODE'] ?? null);
    $oAllStats = json_decode(get('https://api.covid19api.com/summary'));
    $oFoundStats = null;

    if(isset($oAllStats->Global) && isset($oAllStats->Countries))
    {
        if($strCountryCode)
        {
            foreach($oAllStats->Countries as $oCountryStats)
            {
                if($oCountryStats->CountryCode == strtoupper($strCountryCode))
                {
                    $oFoundStats = $oCountryStats;
                    break;
                }
            }
        }
        else
        {
            $oFoundStats = $oAllStats->Global;
        }
    }

    if($oFoundStats)
        print_stats($oFoundStats);
    else
       echo sprintf('No COVID-19 stats found for country code: "%s".', $strCountryCode);
}
catch(Exception $e)
{
    echo $e->getMessage();
}

function print_stats($oStats)
{
    print_r([
        'Title' => 'COVID-19 stats today',
        'Country' => $oStats->Country ?? 'Global',
        'Confirmed cases' => $oStats->NewConfirmed,
        'Recovered' => $oStats->NewRecovered,
        'Deaths' => $oStats->NewDeaths
    ]);
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