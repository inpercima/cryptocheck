<?php
require_once 'transaction.service.php';

$transactionService = new TransactionService();

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
