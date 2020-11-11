<?php
require_once 'core.service.php';

class WalletsService {

  /**
   * constructor
   */
  function __construct() {}

  /**
   * Get information of the used crypto wallets.
   */
  function getWallets() {
    $coreService = new CoreService();
    $response = file_get_contents('https://api.bitpanda.com/v1/wallets', false, $coreService->createContext());
    header('Content-type: application/json');
    return $response;
  }

  /**
   * Get information of the transactions from the used crypto wallets.
   */
  function getTransactions() {
    $coreService = new CoreService();
    $response = file_get_contents('https://api.bitpanda.com/v1/wallets/transactions?page_size=500', false, $coreService->createContext());
    header('Content-type: application/json');
    return $response;
  }

  function getPreparedTransactions() {
    $response = json_decode($this->getTransactions());

    $transactions = [];
    foreach ($response->data as $key => $value) {
      $attributes = $value->attributes;
      $type = $attributes->type;
      $bfc = $attributes->best_fee_collection;
      $bfcBuySell = $attributes->trade->attributes->best_fee_collection;
      array_push($transactions, (object) [
        'type' => $type,
        'asset_id' => intVal($attributes->cryptocoin_id),
        // amount_eur (sell, buy, deposit, transfer as reward) OR best_fee_collection->attributes->bfc_market_value_eur (withdrawal)
        'amount_fiat' => $type == 'withdrawal' ? $bfc->attributes->bfc_market_value_eur : floatVal($attributes->amount_eur),
        // NONE (withdrawal) OR amount (sell, buy, deposit, transfer as reward)
        'amount_coin' => $type == 'withdrawal' ? 0 : floatVal($attributes->amount),
        // NONE (sell, buy, transfer as reward) OR fee (deposit as coins, withdrawal as fiat)
        'fee' => $type == 'deposit' || $type == 'withdrawal' ? floatVal($attributes->fee) : 0,
        'date' => substr($attributes->time->date_iso8601, 0, 10),
        // NONE (deposit, transfer as reward) OR trade->attributes->price (sell, buy) OR best_fee_collection->attributes->best_current_price_eur (withdrawal)
        'price' => $type == 'deposit' || $type == 'transfer' ? 0 : $type == 'withdrawal' ? $bfc->attributes->best_current_price_eur : floatVal($attributes->trade->attributes->price),
        'transaction_id' => $value->id,
        // NONE (deposit, transfer as reward, withdrawal) OR trade->id (sell, buy) OR best_fee_collection->attributes->related_trade->id (withdrawal)
        'trade_id' => $type == 'buy' || $type == 'sell' ? $attributes->trade->id : NULL,
        // NONE (deposit, transfer as reward, sell, buy) OR best_fee_collection->attributes->related_trade->id (withdrawal)
        'ref_trade_id' => $type == 'withdrawal' ? $attributes->best_fee_collection->attributes->related_trade->id : NULL
      ]);
    }
    return json_encode($transactions);
  }
}
?>
