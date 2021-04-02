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
    $walletService = new WalletService();
    $totalCount = $this->save(json_decode($walletService->getPreparedTransactions()));

    $fiatwalletService = new FiatwalletService();
    $totalCount += $this->save(json_decode($fiatwalletService->getPreparedTransactions()));

    return json_encode($totalCount);
  }

  function save($transactions) {
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
      if ($this->mysqlService->count('*', 'transaction', '`transaction_id` = :transaction_id', $countArgs) == 0) {
        $columns = 'asset_id, asset_type, date, price, amount_fiat, amount_asset, fee, type, status, transaction_id, trade_id, ref_trade_id';
        $values = ':asset_id, :asset_type, :date, :price, :amount_fiat, :amount_asset, :fee, :type, :status, :transaction_id, :trade_id, :ref_trade_id';
        $insertArgs = [
          'asset_id' => $value->asset_id, 'asset_type' => $value->asset_type, 'date' => $value->date, 'price' => $value->price,
          'amount_fiat' => $value->amount_fiat, 'amount_asset' => $value->amount_asset,
          'fee' => $value->fee, 'type' => $value->type, 'status' => $value->status,
          'transaction_id' => $value->transaction_id, 'trade_id' => $value->trade_id, 'ref_trade_id' => $value->ref_trade_id
        ];
        $this->mysqlService->insert('transaction', $columns, $values, $insertArgs);

        $count += 1;

        // Wie oben erläutert, wird durch die Drehung erst ein Kauf gespeichert, erst danach wird ein Verkauf verarbeitet.
        // Besteht ein Kauf mit gleicher Wallet-ID und gleicher Anzahl zum Verkauf, dann referenziert der Kauf auf den Verkauf.
        if ($value->type == 'sell') {
          $whereSell = "`type` = 'buy' AND `asset_id` = :asset_id AND `amount_asset` = :amount_asset";
          $updateSellArgs = [
            'ref_transaction_id' => $value->transaction_id, 'asset_id' => $value->asset_id, 'amount_asset' => $value->amount_asset
          ];
          $this->mysqlService->update('transaction', "`ref_transaction_id` = :ref_transaction_id", $whereSell, $updateSellArgs);
        }
      }
    }
    return $count;
  }
}
?>
