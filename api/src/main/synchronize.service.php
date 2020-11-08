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
    $this->save(json_decode($walletsService->getPreparedTransactions()));

    $fiatwalletsService = new FiatwalletsService();
    $this->save(json_decode($fiatwalletsService->getPreparedTransactions()));
  }

  function save($transactions) {
    $reversedTransactions = array_reverse($transactions);
    $mysqlService = new MysqlService();
    $pdo = $mysqlService->connect();

    foreach ($reversedTransactions as $key => $value) {
      $stmtQuery = $pdo->prepare("SELECT COUNT(*) FROM `transaction` WHERE transaction_id = :transaction_id");
      $stmtQuery->bindParam(':transaction_id', $value->transaction_id);
      $stmtQuery->execute();
      // $stmt->rowCount() funktioniert nicht auf mysql bzw. ist nicht garantiert
      if ($stmtQuery->fetchColumn() == 0) {
        $ref_id = NULL;
        $columns = 'asset_id, date, price, amount_fiat, amount_coin, fee, type, transaction_id, ref_id';
        $values = ':asset_id, :date, :price, :amount_fiat, :amount_coin, :fee, :type, :transaction_id, :ref_id';
        $stmt = $pdo->prepare("INSERT INTO `transaction` ({$columns}) VALUES ({$values})");
        $stmt->bindParam(':asset_id', $value->asset_id);
        $stmt->bindParam(':date', $value->date);
        $stmt->bindParam(':price', $value->price);
        $stmt->bindParam(':amount_fiat', $value->amount_fiat);
        $stmt->bindParam(':amount_coin', $value->amount_coin);
        $stmt->bindParam(':fee', $value->fee);
        $stmt->bindParam(':type', $value->type);
        $stmt->bindParam(':transaction_id', $value->transaction_id);
        $stmt->bindParam(':ref_id', $ref_id);
        $stmt->execute();

        if ($value->type == 'sell') {
          $stmtQuery = $pdo->prepare("UPDATE `transaction` SET `ref_id` = :refId  WHERE `type` = 'buy' AND asset_id = :assetId AND amount_coin = :amount_coin");
          $stmtQuery->bindParam(':refId', $pdo->lastInsertId());
          $stmtQuery->bindParam(':amount_coin', $value->amount_coin);
          $stmtQuery->bindParam(':assetId', $value->asset_id);
          $stmtQuery->execute();
        }

      }
    }
    return '';
  }
}
?>
