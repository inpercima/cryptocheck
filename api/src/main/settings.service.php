<?php
require_once 'mysql.service.php';

class SettingsService {

  /**
   * constructor
   */
  function __construct() {}

  /**
   * Get all settings.
   */
  function getAll() {
    $mysqlService = new MysqlService();
    $pdo = $mysqlService->connect();

    $stmt = $pdo->query('SELECT * FROM settings');
    return json_encode($stmt->fetch(PDO::FETCH_ASSOC));
  }

  /**
   * Save all settings.
   */
  function saveAll($data) {
    $mysqlService = new MysqlService();
    $pdo = $mysqlService->connect();

    $stmtQuery = $pdo->prepare('SELECT COUNT(*) FROM settings');
    $stmtQuery->execute();
    // $stmt->rowCount() funktioniert nicht auf mysql bzw. ist nicht garantiert
    if ($stmtQuery->fetchColumn() == 0) {
      $columns = 'currency, ticker, fav1, fav2, fav3, fav4';
      $values = ':currency, :ticker, :fav1, :fav2, :fav3, :fav4';
      $stmt = $pdo->prepare("INSERT INTO settings ({$columns}) VALUES ({$values})");
    } else {
      $columns = 'currency = :currency, ticker = :ticker, fav1 = :fav1, fav2 = :fav2, fav3 = :fav3, fav4 = :fav4';
      $stmt = $pdo->prepare("UPDATE settings SET {$columns}");
    }

    $stmt->bindParam(':currency', $data->currency);
    $stmt->bindParam(':ticker', $data->ticker);
    $stmt->bindParam(':fav1', $data->fav1);
    $stmt->bindParam(':fav2', $data->fav2);
    $stmt->bindParam(':fav3', $data->fav3);
    $stmt->bindParam(':fav4', $data->fav4);
    return json_encode($stmt->execute());
  }
}
?>
