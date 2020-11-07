<?php
require_once 'mysql.service.php';

class AssetService {

  /**
   * constructor
   */
  function __construct() {}

  /**
   * Get information of the assets.
   */
  function getAssets() {
    $mysqlService = new MysqlService();
    $pdo = $mysqlService->connect();

    $stmt = $pdo->query('SELECT `name` FROM `asset` WHERE `id` > 0  ORDER BY `name` ASC');
    return json_encode(array_column($stmt->fetchAll(PDO::FETCH_ASSOC), 'name'));
  }
}
?>
