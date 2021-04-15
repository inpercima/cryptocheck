<?php
require_once '../service/core.service.php';
$coreService = new CoreService();

require_once $coreService->requireConfig();
require_once '../service/binance.service.php';

$coreService->setHeader();

$binanceService = new BinanceService();
echo $binanceService->getInfo();
?>
