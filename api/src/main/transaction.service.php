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
    SELECT ROUND(
      (SELECT COALESCE(SUM(`amount_fiat` + `fee`), 0)
        FROM `transaction`
        JOIN `asset` ON `transaction`.`asset_id` = `asset`.`id`
        WHERE `asset`.`name` = (SELECT `currency` FROM `settings`)
        AND `transaction_id` IS NOT NULL
      ), 2) AS `internal`,
      ROUND(
        (SELECT COALESCE(SUM(`amount_fiat`), 0)
          FROM `transaction`
          WHERE `type` = 'buy'
          AND `transaction_id` IS NULL
        ), 2) AS `external`");
    $stmt->execute();
    $investment = $stmt->fetch(PDO::FETCH_ASSOC);

    $stmt = $pdo->prepare("
      SELECT ROUND(
        (SELECT ROUND(
          (SELECT COALESCE(SUM(`amount_fiat`), 0)
            FROM `transaction`
            JOIN `asset` ON `transaction`.`asset_id` = `asset`.`id`
            WHERE `asset`.`name` = (SELECT `currency` FROM `settings`)
          )
        , 2))
        -
        (SELECT ROUND(
          (SELECT COALESCE(SUM(`amount_fiat`), 0)
            FROM `transaction`
            WHERE `type` = 'buy'
            AND `transaction_id` IS NOT NULL
          ) -
          (SELECT COALESCE(SUM(`amount_fiat`), 0)
            FROM `transaction`
            WHERE `type` = 'sell'
            AND `transaction_id` IS NOT NULL
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
      FROM `transaction`
      JOIN `asset`ON `transaction`.`asset_id` = `asset`.`id`
      WHERE `transaction`.`asset_id` > 0");
    $stmt->execute();
    $coins = $stmt->fetchAll(PDO::FETCH_ASSOC);

    $cryptos = [];

    foreach ($coins as $key => $value) {
      $stmt = $pdo->prepare("
        SELECT ROUND(
          (SELECT COALESCE(SUM(`amount_fiat`), 0)
            FROM `transaction`
            WHERE `type` IN ('buy', 'transfer')
            AND ref_transaction_id IS NULL AND `asset_id` = :assetId
            AND `transaction_id` IS NOT NULL
          ) -
          (SELECT COALESCE(SUM(`amount_fiat`), 0)
            FROM `transaction`
            WHERE `type` IN ('withdrawal')
            AND `asset_id` = :assetId
          )
        , 2) AS `fiat`,
        ROUND(
          (SELECT COALESCE(SUM(`amount_fiat`), 0)
            FROM `transaction`
            WHERE `type` IN ('buy')
            AND `asset_id` = :assetId
            AND `transaction_id` IS NULL
          )
        , 2) AS `investment_external`,
        ROUND(
          (SELECT COALESCE(SUM(`amount_coin`), 0)
            FROM `transaction`
            WHERE `type` IN ('buy', 'transfer', 'deposit')
            AND ref_transaction_id IS NULL AND `asset_id` = :assetId
            AND `transaction_id` IS NOT NULL
          ) -
          (SELECT COALESCE(SUM(`fee`), 0)
            FROM `transaction`
            WHERE `type` IN ('withdrawal')
            AND `asset_id` = :assetId
          )
        , 8) AS `coin`");
      $stmt->bindParam(':assetId', $value['id']);
      $stmt->execute();
      $crypto = $stmt->fetch(PDO::FETCH_ASSOC);
      if ($crypto['coin'] > 0) {
        array_push($cryptos, (object) [
          'fiat' => $crypto['fiat'],
          'external' => $crypto['investment_external'],
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
