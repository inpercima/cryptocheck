<?php
require_once '../service/core.service.php';
$coreService = new CoreService();

require_once $coreService->requireConfig();
require_once '../service/fiatwallet.service.php';

$coreService->setHeader();

$fiatwalletService = new FiatwalletService();
if ($coreService->pathInfo('transactions')) {
  echo $fiatwalletService->getTransactions();
} else if ($coreService->pathInfo('used')) {
  echo $fiatwalletService->getPreparedWallets();
} else if ($coreService->pathInfo('investments')) {
  echo $fiatwalletService->getInvestments();
} else {
  echo $fiatwalletService->getWallets();
}
?>
