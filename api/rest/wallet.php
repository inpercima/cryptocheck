<?php
require_once '../service/core.service.php';
$coreService = new CoreService();

require_once $coreService->requireConfig();
require_once '../service/wallet.service.php';

$coreService->setHeader();

$walletService = new WalletService();
if ($coreService->pathInfo('transactions')) {
  echo $walletService->getTransactions();
} else if ($coreService->pathInfo('used')) {
  echo $walletService->getPreparedWallets();
} else {
  echo $walletService->getWallets();
}
?>
