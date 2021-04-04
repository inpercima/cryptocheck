<?php
require_once '../service/core.service.php';
$coreService = new CoreService();

require_once $coreService->requireConfig();
require_once '../service/synchronize.service.php';

$coreService->setHeader();

$synchronizeService = new SynchronizeService();
echo $synchronizeService->synchronize();
?>
