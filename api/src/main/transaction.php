<?php
require_once 'transaction.service.php';

$transactionService = new TransactionService();
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Headers: Content-Type");

$query = $_SERVER['QUERY_STRING'];

if ($query) {
  parse_str($query, $queryArr);
  $type = $queryArr['type'];
  switch ($type) {
    case 'fiat':
      echo $transactionService->getFiat();
      break;
    case 'crypto':
      echo $transactionService->getCrypto();
      break;
    default:
      break;
  }
} else {
  echo '';
}
?>
