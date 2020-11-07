<?php
require_once 'core.service.php';
require_once 'mysql.service.php';

class FiatwalletsService {

  /**
   * constructor
   */
  function __construct() {}

  /**
   * Get information of the used fiat wallets.
   */
  function getFiatwallets() {
    $coreService = new CoreService();
    $response = file_get_contents('https://api.bitpanda.com/v1/fiatwallets', false, $coreService->createContext());
    header('Content-type: application/json');
    return $response;
  }

  /**
   * Get information of the transactions of the used fiat wallets.
   */
  function getTransactions() {
    $coreService = new CoreService();
    $response = file_get_contents('https://api.bitpanda.com/v1/fiatwallets/transactions?page_size=500', false, $coreService->createContext());
    header('Content-type: application/json');
    return $response;
  }

  function getPreparedTransactions() {
    $response = json_decode($this->getTransactions());

    $transactions = [];
    foreach ($response->data as $key => $value) {
      $attributes = $value->attributes;
      if ($attributes->type == 'deposit' && $attributes->status == 'finished') {
        array_push($transactions, (object) [
          'type' => $attributes->type,
          'asset_id' => -1,
          'amount_fiat' => floatVal($attributes->amount),
          'amount_coin' => 0,
          'fee' => floatVal($attributes->fee),
          'date' => substr($attributes->time->date_iso8601, 0, 10),
          'price' => 0,
          'transaction_id' => $value->id
        ]);
      }
    }
    return json_encode($transactions);
  }
}
?>
