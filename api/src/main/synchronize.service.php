<?php
require_once 'fiatwallets.service.php';
require_once 'mysql.service.php';
require_once 'wallets.service.php';

class SynchronizeService {

  /**
   * constructor
   */
  function __construct() {}

  function saveAll() {
    $walletsService = new WalletsService();
    $totalCount = $this->save(json_decode($walletsService->getPreparedTransactions()));

    $fiatwalletsService = new FiatwalletsService();
    $totalCount += $this->save(json_decode($fiatwalletsService->getPreparedTransactions()));

    return json_encode($totalCount);
  }

  function save($transactions) {
    $reversedTransactions = array_reverse($transactions);
    $mysqlService = new MysqlService();
    $pdo = $mysqlService->connect();

    $count = 0;
    foreach ($reversedTransactions as $key => $value) {
      $stmtQuery = $pdo->prepare("SELECT COUNT(*) FROM `transaction` WHERE transaction_id = :transaction_id");
      $stmtQuery->bindParam(':transaction_id', $value->transaction_id);
      $stmtQuery->execute();
      // $stmt->rowCount() funktioniert nicht auf mysql bzw. ist nicht garantiert
      if ($stmtQuery->fetchColumn() == 0) {
        $columns = 'asset_id, date, price, amount_fiat, amount_coin, fee, type, transaction_id, trade_id';
        $values = ':asset_id, :date, :price, :amount_fiat, :amount_coin, :fee, :type, :transaction_id, :trade_id';
        $stmt = $pdo->prepare("INSERT INTO `transaction` ({$columns}) VALUES ({$values})");
        $stmt->bindParam(':asset_id', $value->asset_id);
        $stmt->bindParam(':date', $value->date);
        $stmt->bindParam(':price', $value->price);
        $stmt->bindParam(':amount_fiat', $value->amount_fiat);
        $stmt->bindParam(':amount_coin', $value->amount_coin);
        $stmt->bindParam(':fee', $value->fee);
        $stmt->bindParam(':type', $value->type);
        $stmt->bindParam(':transaction_id', $value->transaction_id);
        $stmt->bindParam(':trade_id', $value->trade_id);
        $stmt->execute();
        $count += 1;

        if ($value->type == 'sell') {
          $stmtQuery = $pdo->prepare("UPDATE `transaction` SET `ref_transaction_id` = :ref_transaction_id WHERE `type` = 'buy' AND asset_id = :asset_id AND amount_coin = :amount_coin");
          $stmtQuery->bindParam(':ref_transaction_id', $value->transaction_id);
          $stmtQuery->bindParam(':asset_id', $value->asset_id);
          $stmtQuery->bindParam(':amount_coin', $value->amount_coin);
          $stmtQuery->execute();
        }
        if ($value->type == 'withdrawal') {
          $stmtQuery = $pdo->prepare("UPDATE `transaction` SET `ref_trade_id` = :ref_trade_id WHERE `transaction_id` = :transaction_id");
          $stmtQuery->bindParam(':ref_trade_id', $value->ref_trade_id);
          $stmtQuery->bindParam(':transaction_id', $value->transaction_id);
          $stmtQuery->execute();
        }
      }
    }
    return $count;
  }
}
?>
