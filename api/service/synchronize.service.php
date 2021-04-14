<?php
require_once 'fiatwallet.service.php';
require_once 'mysql.service.php';
require_once 'wallet.service.php';

class SynchronizeService {

  private $mysqlService;

  /**
   * constructor
   */
  function __construct() {
    $this->mysqlService = new MysqlService();
  }

  function synchronize() {
    $fiatwalletService = new FiatwalletService();
    $totalCount = $this->saveFiat(json_decode($fiatwalletService->getPreparedTransactions()));

    $walletService = new WalletService();
    $totalCount += $this->saveAsset(json_decode($walletService->getPreparedTransactions()));

    $this->checkTrades(json_decode($walletService->getUncheckedTransactions()));

    return json_encode($totalCount);
  }

  function saveFiat($transactions) {
    // Im Gegensatz zu Assets wäre hier keine Drehung notwendig, damit aber die Reihenfolgen passen, wird es auch hier gedreht.
    $reversedTransactions = array_reverse($transactions);

    $count = 0;
    foreach ($reversedTransactions as $key => $value) {
      $countArgs = ['transaction_id' => $value->transaction_id];
      if ($this->mysqlService->count('*', 'transaction_fiat', '`transaction_id` = :transaction_id', $countArgs) == 0) {
        $columns = 'type_fiat_id, date, amount, fee, type, status, transaction_id';
        $values = ':type_fiat_id, :date, :amount, :fee, :type, :status, :transaction_id';
        $insertArgs = [
          'type_fiat_id' => $value->type_fiat_id, 'date' => $value->date, 'amount' => $value->amount,
          'fee' => $value->fee, 'type' => $value->type, 'status' => $value->status, 'transaction_id' => $value->transaction_id
        ];
        $count += $this->mysqlService->insert('transaction_fiat', $columns, $values, $insertArgs)->rowCount();
      }
    }
    return $count;
  }


  function saveAsset($transactions) {
    // Die Listen der Transaktionen beginnen immer mit der letzten Transaktion (0 letzte, 1 vorletzte usw.).
    // Aus logischer Sicht ist das auch passend, denn dann kommt nach einem Kauf (id 1), der Verkauf (id 0).
    // Da aber der erste Eintrag auch bedeutet, dass dieser als erstes verarbeitet wird, ist dann erst ein Verkauf
    // und dann ein Kauf in Verarbeitung.
    // Das hat zur Folge, dass ein Verkauf sich nicht auf einen Kauf referenzieren kann.
    // Damit ein Kauf eines Assets und der Verkauf aller Anteile dieses Assets auch referenziert werden kann,
    // muss die Reihenfolge gedreht werden.

    // Durch die Drehung werden auch die Angaben von BEST-Transaktionen gedreht.
    // In der Reihenfolge würde erst ein Kauf, dann die BEST-Transaktion folgen, nun kommt aber erst die BEST-Transaktion und dann der Kauf.
    // Daher wird bereits bei der Ermittlung der Daten im Vorfeld der zur BEST-Transaktion gehörende Trade ermittelt
    // und als ref_trade_id übergeben.
    $reversedTransactions = array_reverse($transactions);

    $count = 0;
    foreach ($reversedTransactions as $key => $value) {
      $countArgs = ['transaction_id' => $value->transaction_id];
      if ($this->mysqlService->count('*', 'transaction_asset', '`transaction_id` = :transaction_id', $countArgs) == 0) {
        $columns = 'type_asset_id, date, price, amount, number, fee, type, status, transaction_id, trade_id, ref_trade_id';
        $values = ':type_asset_id, :date, :price, :amount, :number, :fee, :type, :status, :transaction_id, :trade_id, :ref_trade_id';
        $insertArgs = [
          'type_asset_id' => $value->type_asset_id, 'date' => $value->date, 'price' => $value->price,
          'amount' => $value->amount, 'number' => $value->number,
          'fee' => $value->fee, 'type' => $value->type, 'status' => $value->status,
          'transaction_id' => $value->transaction_id, 'trade_id' => $value->trade_id, 'ref_trade_id' => $value->ref_trade_id
        ];
        $count += $this->mysqlService->insert('transaction_asset', $columns, $values, $insertArgs)->rowCount();

        // Wie oben erläutert, wird durch die Drehung erst ein Kauf gespeichert, erst danach wird ein Verkauf verarbeitet.
        // Besteht ein Kauf mit gleicher Wallet-ID und gleicher Anzahl zum Verkauf, dann referenziert der Kauf auf den Verkauf.
        if ($value->type == 'sell') {
          $whereSell = "`type` = 'buy' AND `type_asset_id` = :type_asset_id AND `number` = :number";
          $updateSellArgs = [
            'ref_transaction_id' => $value->transaction_id, 'type_asset_id' => $value->type_asset_id, 'number' => $value->number
          ];
          $this->mysqlService->update('transaction_asset', "`ref_transaction_id` = :ref_transaction_id", $whereSell, $updateSellArgs);
        }
      }
    }
    return $count;
  }

  function getNumbers($asset) {
    return $asset->number;
  }

  function getIds($asset) {
    return $asset->transactionId;
  }

  function checkTrades($transactions) {
    $buyList = [];
    foreach ($transactions as $key => $value) {
      $buy = (object) [
        'name' => $value->name,
        'number' => $value->number,
        'transactionId' => $value->transaction_id,
      ];
      if ($value->type == 'buy' && (empty($buyList) || (!empty($buyList) && $buyList[0]->name == $value->name))) {
        array_push($buyList, $buy);
      } else if ($value->type == 'sell' && $buyList[0]->name == $value->name) {
        $numbers = array_map(array($this, 'getNumbers'), $buyList);
        // number_format to avoid round problems
        if (number_format($value->number, 8) - number_format(array_sum($numbers), 8) == 0) {
          $ids = "'" . implode("','", array_map(array($this, 'getIds'), $buyList)) . "'";
          $whereBuy = "`transaction_id` IN (" . $ids . ")";
          $updateBuyArgs = ['ref_transaction_id' => $value->transaction_id];
          $this->mysqlService->update('transaction_asset', "`ref_transaction_id` = :ref_transaction_id", $whereBuy, $updateBuyArgs);
        }
        $buyList = [];
      } else {
        $buyList = [];
        array_push($buyList, $buy);
      }
    }
  }
}
?>
