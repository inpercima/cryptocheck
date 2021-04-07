<?php
require_once '../service/core.service.php';
$coreService = new CoreService();

require_once $coreService->requireConfig();
require_once '../service/wallet.service.php';

$coreService->setHeader();

$walletService = new WalletService();
switch ($_SERVER['REQUEST_METHOD']) {
  case 'GET':
    if ($coreService->pathInfo('transactions')) {
      echo $walletService->getTransactions();
    } else if ($coreService->pathInfo('transactions/unchecked')) {
      echo $walletService->getUncheckedTransactions();
    } else if ($coreService->pathInfo('used')) {
      echo $walletService->getPreparedWallets();
    } else if ($coreService->pathInfo('investments')) {
      echo $walletService->getInvestments($coreService->getParam('cryptoIds'));
    } else if ($coreService->pathInfo('profitloss/trades')) {
      echo $walletService->getProfitLossPerTrade();
    } else if ($coreService->pathInfo('profitloss/trades/month')) {
      echo $walletService->getProfitLossPerTradeCurrentMonth();
    } else {
      echo $walletService->getWallets();
    }
    break;
  case 'POST':
    break;
  default:
    break;
}
?>
