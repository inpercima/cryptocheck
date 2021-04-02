<?php
require_once '../service/core.service.php';
$coreService = new CoreService();

require_once $coreService->requireConfig();
require_once '../service/trade.service.php';

$coreService->setHeader();

$tradeService = new TradeService();
echo $tradeService->getTrades();
?>
