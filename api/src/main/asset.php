<?php
require_once 'asset.service.php';

$assetService = new AssetService();
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Headers: Content-Type");

echo $assetService->getAssets();
?>
