<?php
require_once '../service/core.service.php';
$coreService = new CoreService();

require_once $coreService->requireConfig();
require_once '../service/asset.service.php';

$coreService->setHeader();

$assetService = new AssetService();
echo $assetService->getAssets();
?>
