<?php
require_once 'trades.service.php';

$tradesService = new TradesService();

echo $tradesService->getTrades();
?>
