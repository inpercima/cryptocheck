<?php
require_once 'core.service.php';
require_once 'mysql.service.php';

class WalletService {

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
          'id' => $attributes->cryptocoin_id,
          'symbol' => $attributes->cryptocoin_symbol,
          'balance' => $attributes->balance,
          'name' => $attributes->name
        ]);
      }
    }
    return json_encode($wallets, JSON_NUMERIC_CHECK);
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
        'type_asset_id' => intVal($attributes->cryptocoin_id),
        // Betrag
        // amount_eur (sell, buy, deposit, transfer as reward for BEST) OR best_fee_collection->attributes->bfc_market_value_eur (withdrawal)
        'amount' => $type == 'withdrawal' ? $bfc->attributes->bfc_market_value_eur : floatVal($attributes->amount_eur),
        // Anzahl der gehandelten assets
        // 0 (withdrawal) OR amount_asset (sell, buy, deposit, transfer as reward for BEST)
        'number' => $type == 'withdrawal' ? 0 : floatVal($attributes->amount),
        // mögliche Gebühr bspw. bei einer Einzahlung
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
    return json_encode($transactions, JSON_NUMERIC_CHECK);
  }

  function getInvestments($crypoIds) {
    $investments = [];
    foreach (explode(',', $crypoIds) as $key => $value) {
      $result = $this->mysqlService->queryOne("
      SELECT
        ROUND(
          (SELECT COALESCE(SUM(`amount`), 0)
            FROM `transaction_asset`
            WHERE `type_asset_id` = :type_asset_id AND `type` IN ('buy', 'transfer')
            AND `transaction_id` IS NOT NULL
            AND `ref_transaction_id` IS NULL
          ), 2
        ) AS `internal`,
        ROUND(
          (SELECT COALESCE(SUM(`amount`), 0)
            FROM `transaction_external`
            WHERE `type_asset_id` = :type_asset_id AND `type` IN ('buy')
          ), 2
        ) AS `external`", ['type_asset_id' => $value]);
      $investments[trim($value)] = $result;
    }
    return json_encode($investments, JSON_NUMERIC_CHECK);
  }

  function getProfitLossPerTrade() {
    $result = $this->mysqlService->queryAll("
    SELECT `t1`.`type_asset_id`, `t1`.`date` AS `begin`, `t2`.`date` AS `end`, `t1`.`amount` AS `buy`, `t2`.`amount` AS `sell`, `t1`.`number`,
    ROUND(`t2`.`amount` - SUM(`t1`.`amount`), 2) AS `total`
    FROM `transaction_asset` AS `t1`
    JOIN `transaction_asset` AS `t2`
    ON `t1`.`ref_transaction_id` = `t2`.`transaction_id`
    AND `t1`.`status` = 'finished'
    AND `t2`.`status` = 'finished'
    GROUP BY `sell`");
    return json_encode($result, JSON_NUMERIC_CHECK);
  }

  function getProfitLossPerTradeCurrentMonth() {
    $result = $this->mysqlService->queryAll("
    SELECT `t1`.`type_asset_id`, `t1`.`date` AS `begin`, `t2`.`date` AS `end`, `t1`.`amount` AS `buy`, `t2`.`amount` AS `sell`, `t1`.`number`,
    ROUND(`t2`.`amount` - SUM(`t1`.`amount`), 2) AS `total`
    FROM `transaction_asset` AS `t1`
    JOIN `transaction_asset` AS `t2`
    ON `t1`.`ref_transaction_id` = `t2`.`transaction_id`
    WHERE (t2.`date` BETWEEN :date_begin AND :date_end)
    AND `t1`.`status` = 'finished'
    AND `t2`.`status` = 'finished'
    GROUP BY `sell`", ['date_begin' => date('Y-m-01'), 'date_end' => date('Y-m-t')]);
    return json_encode($result, JSON_NUMERIC_CHECK);
  }

  function getUncheckedTransactions() {
    $result = $this->mysqlService->queryAll("
    SELECT * FROM
      (SELECT `transaction_id`, `type_asset`.`name`, `amount`, `number`, `type`, `date`
        FROM `transaction_asset`
        JOIN `type_asset` ON `type_asset`.`id` = `transaction_asset`.`type_asset_id`
        WHERE `status` = 'finished'
        AND `type` = 'buy'
        AND `type_asset_id` != 33
        AND `ref_transaction_id` IS NULL
      UNION ALL
      SELECT `transaction_id`, `type_asset`.`name`, `amount`, `number`, `type`, `date`
        FROM `transaction_asset` AS `t1`
        JOIN `type_asset` ON `type_asset`.`id` = `t1`.`type_asset_id`
        WHERE NOT EXISTS (SELECT 1 FROM `transaction_asset` AS `t2` WHERE `t1`.`transaction_id` = `t2`.ref_transaction_id)
        AND `status` = 'finished'
        AND `type` = 'sell'
        AND `type_asset_id` != 33) AS `all`
      ORDER BY `name`, `date`, `type`");
    return json_encode($result, JSON_NUMERIC_CHECK);
  }
}
?>
