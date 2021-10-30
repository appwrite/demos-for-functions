<?php
include './vendor/autoload.php';

$speedtest = new Aln\Speedtest\Speedtest();
$speedtest->getServers();
$speedtest->getBestServer();
$speedtest->download();
$speedtest->upload();
$results = $speedtest->results();
$output = ["DownloadSpeed" => round((float)$results->getDownload() / 1000 / 1000, 2) . "Mbps", "UploadSpeed" => round((float)$results->getUpload() / 1000 / 1000, 2) . "Mbps"];
print_r(json_encode($output));
