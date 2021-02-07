<?php
require_once 'wallets.service.php';

$walletsService = new WalletsService();
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Headers: Content-Type");

$query = $_SERVER['QUERY_STRING'];

if ($query) {
  parse_str($query, $queryArr);
  $transactionsType = $queryArr['transactions'];

  echo $walletsService->getTransactions($transactionsType);
} else {
  echo $walletsService->getWallets();
}
?>
