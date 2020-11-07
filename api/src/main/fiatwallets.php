<?php
require_once 'fiatwallets.service.php';

$fiatwalletsService = new FiatwalletsService();

$query = $_SERVER['QUERY_STRING'];

if ($query) {
  parse_str($query, $queryArr);
  $transactionsType = $queryArr['transactions'];

  echo $fiatwalletsService->getTransactions($transactionsType);
} else {
  echo $fiatwalletsService->getFiatwallets();
}
?>
