<?php
require_once 'mysql.service.php';

class TransactionService {

  /**
   * constructor
   */
  function __construct() {}

  /**
   * Get information of fiat transactions.
   */
  function getFiat() {
    $mysqlService = new MysqlService();
    $pdo = $mysqlService->connect();

    $stmt = $pdo->prepare("
      SELECT COALESCE(SUM(`amount_fiat` + `fee`), 0) AS `investment`
      FROM `transaction_test`
      JOIN `asset` ON `transaction_test`.`asset_id` = `asset`.`id`
      WHERE `asset`.`name` = (SELECT `currency` FROM `settings`)");
    $stmt->execute();
    $investment = $stmt->fetchColumn();

    $stmt = $pdo->prepare("
      SELECT ROUND(
        (SELECT ROUND(
          (SELECT COALESCE(SUM(`amount_fiat`), 0)
            FROM `transaction_test`
            JOIN `asset` ON `transaction_test`.`asset_id` = `asset`.`id`
            WHERE `asset`.`name` = (SELECT `currency` FROM `settings`)
          )
        , 2))
        -
        (SELECT ROUND(
          (SELECT COALESCE(SUM(`amount_fiat`), 0)
            FROM `transaction_test`
            WHERE `type` = 'buy'
          ) -
          (SELECT COALESCE(SUM(`amount_fiat`), 0)
            FROM `transaction_test`
            WHERE `type` = 'sell'
          )
        , 2))
      , 2) AS `fiatWallet`");
    $stmt->execute();
    $value = $stmt->fetchColumn();

    header('Access-Control-Allow-Origin: *');
    header('Content-type: application/json');
    return json_encode(array(
      'investment' => $investment,
      'value' => $value
    ), JSON_NUMERIC_CHECK);
  }

  /**
   * Get information of crypto transactions.
   */
  function getCrypto() {
    $mysqlService = new MysqlService();
    $pdo = $mysqlService->connect();

    $stmt = $pdo->prepare("
      SELECT DISTINCT `asset`.`id`, `asset`.`name`
      FROM `transaction_test`
      JOIN `asset`ON `transaction_test`.`asset_id` = `asset`.`id`
      WHERE `transaction_test`.`asset_id` > 0");
    $stmt->execute();
    $coins = $stmt->fetchAll(PDO::FETCH_ASSOC);

    $cryptos = [];

    foreach ($coins as $key => $value) {
      $stmt = $pdo->prepare("
        SELECT ROUND(
          (SELECT COALESCE(SUM(`amount_fiat`), 0)
            FROM `transaction_test`
            WHERE `type` IN ('buy', 'transfer', 'deposit') AND ref_id IS NULL AND `asset_id` = :assetId
          ) -
          (SELECT COALESCE(SUM(`amount_fiat`), 0)
            FROM `transaction_test`
            WHERE `type` IN ('withdrawal') AND `asset_id` = :assetId
          )
        , 2) AS `fiat`,
        ROUND(
          (SELECT COALESCE(SUM(`amount_coin`), 0)
            FROM `transaction_test`
            WHERE `type` IN ('buy', 'transfer', 'deposit') AND ref_id IS NULL AND `asset_id` = :assetId
          ) -
          (SELECT COALESCE(SUM(`fee`), 0)
            FROM `transaction_test`
            WHERE `type` IN ('withdrawal') AND `asset_id` = :assetId
          )
        , 8) AS `coin`");
      $stmt->bindParam(':assetId', $value['id']);
      $stmt->execute();
      $crypto = $stmt->fetch(PDO::FETCH_ASSOC);
      if ($crypto['coin'] > 0) {
        array_push($cryptos, (object) [
          'fiat' => $crypto['fiat'],
          'coins' => $crypto['coin'],
          'name' => $value['name']
        ]);
      }
    }

    header('Access-Control-Allow-Origin: *');
    header('Content-type: application/json');
    return json_encode($cryptos, JSON_NUMERIC_CHECK);
  }
}
?>
