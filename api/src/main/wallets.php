<?php
require_once 'wallets.service.php';

$walletsService = new WalletsService();

$query = $_SERVER['QUERY_STRING'];

if ($query) {
  parse_str($query, $queryArr);
  $transactionsType = $queryArr['transactions'];

  echo $walletsService->getTransactions($transactionsType);
} else {
  echo $walletsService->getWallets();
}
?>
