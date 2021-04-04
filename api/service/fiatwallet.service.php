<?php
require_once 'core.service.php';
require_once 'mysql.service.php';

class FiatwalletService {

  private $coreService;

  private $mysqlService;

  /**
   * constructor
   */
  function __construct() {
    $this->coreService = new CoreService();
    $this->mysqlService = new MysqlService();
  }

  /**
   * Get information of the used fiat wallets.
   */
  function getWallets() {
    return file_get_contents('https://api.bitpanda.com/v1/fiatwallets', false, $this->coreService->createContext());
  }

  function getPreparedWallets() {
    $response = json_decode($this->getWallets());

    $wallets = [];
    foreach ($response->data as $key => $value) {
      $attributes = $value->attributes;
      if ($attributes->balance > 0) {
        array_push($wallets, (object) [
          'id' => $attributes->fiat_id,
          'symbol' => $attributes->fiat_symbol,
          'balance' => $attributes->balance,
          'name' => $attributes->name
        ]);
      }
    }
    return json_encode($wallets, JSON_NUMERIC_CHECK);
  }

  /**
   * Get information of the transactions of the used fiat wallets.
   *
   * Types:
   * buy        - from fiat to asset
   * sell       - from asset to fiat
   * deposit    - from bank to fiat
   * withdrawal - from fiat to bank
   * transfer   -
   * refund     -
   */
  function getTransactions() {
    return file_get_contents('https://api.bitpanda.com/v1/fiatwallets/transactions?page_size=500', false, $this->coreService->createContext());
  }

  /**
   * Ermittelt alle Transaktionen der Fiat-Walltes, außer den Typen 'buy' und 'sell', da diese auch unter den Transaktionen der
   * Asset-Wallets ermittelt werden können.
   *
   * Es werden sowohl erfolgreiche als auch fehlgeschlagene Transaktionen ermittelt, um einen vollen Bestand zu haben.
   */
  function getPreparedTransactions() {
    $response = json_decode($this->getTransactions());

    $transactions = [];
    foreach ($response->data as $key => $value) {
      $attributes = $value->attributes;
      if ($attributes->type != 'buy' && $attributes->type != 'sell') {
        array_push($transactions, (object) [
          // eines der Typen deposit, withdrawal, transfer, refund
          'type' => $attributes->type,
          // die ID des Fialt-Typs (bspw. EUR = 1)
          'type_fiat_id' => $attributes->fiat_id,
          // Betrag
          'amount' => floatVal($attributes->amount),
          // mögliche Gebühr bspw. bei einer sofortigen Einzahlung
          'fee' => floatVal($attributes->fee),
          // Datum
          'date' => substr($attributes->time->date_iso8601, 0, 10),
          // eines der Typen pending, processing, finished, canceled
          'status' => $attributes->status,
          // eindeutige Nummer der Transaktion
          'transaction_id' => $value->id
        ]);
      }
    }
    return json_encode($transactions, JSON_NUMERIC_CHECK);
  }

  function getInvestments() {
    $investments = [];
    $result = $this->mysqlService->queryOne("
    SELECT
      ROUND(
        (SELECT
          ROUND(
            (SELECT COALESCE(SUM(`amount` + `fee`), 0)
              FROM `transaction_fiat`
              JOIN `type_fiat` ON `transaction_fiat`.`type_fiat_id` = `type_fiat`.`id`
              WHERE `type_fiat`.`name` = (SELECT `currency` FROM `settings`)
              AND `status` = 'finished'
              AND `type` = 'deposit'
            ), 2
          )
        )
        -
        (SELECT
          ROUND(
            (SELECT COALESCE(SUM(`amount`), 0)
              FROM `transaction_fiat`
              JOIN `type_fiat` ON `transaction_fiat`.`type_fiat_id` = `type_fiat`.`id`
              WHERE `type_fiat`.`name` = (SELECT `currency` FROM `settings`)
              AND `status` = 'finished'
              AND `type` = 'withdrawal'
            ), 2
          )
        ), 2
      )
    AS `investment`");
    $investments[1] = $result['investment'];
    return json_encode($investments, JSON_NUMERIC_CHECK);
  }
}
?>
