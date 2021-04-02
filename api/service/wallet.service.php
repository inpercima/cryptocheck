<?php
require_once 'core.service.php';

class WalletService {

  private $coreService;

  /**
   * constructor
   */
  function __construct() {
    $this->coreService = new CoreService();
  }

  /**
   * Get information of the used crypto wallets.
   */
  function getWallets() {
    return file_get_contents('https://api.bitpanda.com/v1/wallets', false, $this->coreService->createContext());
  }

  function getPreparedWallets() {
    $response = json_decode($this->getWallets());

    $wallets = [];
    foreach ($response->data as $key => $value) {
      $attributes = $value->attributes;
      if ($attributes->balance > 0) {
        array_push($wallets, (object) [
          'symbol' => $attributes->cryptocoin_symbol,
          'balance' => $attributes->balance,
          'name' => $attributes->name
        ]);
      }
    }
    return json_encode($wallets);
  }

  /**
   * Get information of the transactions of the used crypto wallets.
   *
   * Types:
   * buy        - from fiat to asset
   * sell       - from asset to fiat
   * deposit    - from another wallet to bitpanda wallet
   * withdrawal - fee to bitpanda
   * transfer   - rewards from bitpanda
   * refund     -
   * ico        -
   */
  function getTransactions() {
    return file_get_contents('https://api.bitpanda.com/v1/wallets/transactions?page_size=500', false, $this->coreService->createContext());
  }

  /**
   * Ermittelt alle Transaktionen der Asset-Walltes.
   *
   * Es werden sowohl erfolgreiche als auch fehlgeschlagene Transaktionen ermittelt, um einen vollen Bestand zu haben.
   */
  function getPreparedTransactions() {
    $response = json_decode($this->getTransactions());

    $transactions = [];
    foreach ($response->data as $key => $value) {
      $attributes = $value->attributes;
      $type = $attributes->type;
      $bfc = $attributes->best_fee_collection;
      $bfcBuySell = $attributes->trade->attributes->best_fee_collection;
      array_push($transactions, (object) [
        // eines der Typen buy, sell, deposit, withdrawal, transfer, refund, ico
        'type' => $type,
        // die ID des Asset-Typs (bspw. BTC = 1)
        'asset_id' => intVal($attributes->cryptocoin_id),
        // Typ des Wallets, bei Crypto immer CRYPTO
        'asset_type' => 'CRYPTO',
        // Betrag
        // amount_eur (sell, buy, deposit, transfer as reward for BEST) OR best_fee_collection->attributes->bfc_market_value_eur (withdrawal)
        'amount_fiat' => $type == 'withdrawal' ? $bfc->attributes->bfc_market_value_eur : floatVal($attributes->amount_eur),
        // Anzahl der gehandelten assets
        // 0 (withdrawal) OR amount_asset (sell, buy, deposit, transfer as reward for BEST)
        'amount_asset' => $type == 'withdrawal' ? 0 : floatVal($attributes->amount),
        // mögliche Gebühr bspw. bei einer sofortigen Einzahlung
        // 0 (sell, buy, transfer as reward for BEST) OR fee (deposit as assets, withdrawal as fiat)
        'fee' => $type == 'deposit' || $type == 'withdrawal' ? floatVal($attributes->fee) : 0,
        // Datum
        'date' => substr($attributes->time->date_iso8601, 0, 10),
        // eines der Typen pending, processing, unconfirmed_transaction_out, open_invitation, finished, canceled
        'status' => $attributes->status,
        // Preis zu dem ein Asset gehandelt wurde
        // 0 (deposit, transfer as reward for BEST) OR trade->attributes->price (sell, buy) OR best_fee_collection->attributes->best_current_price_eur (withdrawal)
        'price' => $type == 'deposit' || $type == 'transfer' ? 0 : $type == 'withdrawal' ? $bfc->attributes->best_current_price_eur : floatVal($attributes->trade->attributes->price),
        // eindeutige Nummer der Transaktion
        'transaction_id' => $value->id,
        // ref_transaction_id, wird nicht gefüllt und ist default NULL
        // NULL (deposit, transfer as reward for BEST, withdrawal) OR trade->id (sell, buy)
        'trade_id' => $type == 'buy' || $type == 'sell' ? $attributes->trade->id : NULL,
        // NULL (deposit, transfer as reward for BEST, sell, buy) OR best_fee_collection->attributes->related_trade->id (withdrawal)
        'ref_trade_id' => $type == 'withdrawal' ? $attributes->best_fee_collection->attributes->related_trade->id : NULL
      ]);
    }
    return json_encode($transactions);
  }
}
?>
