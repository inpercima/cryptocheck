<?php
require_once 'mysql.service.php';

class SettingsService {

  private $mysqlService;

  /**
   * constructor
   */
  function __construct() {
    $this->mysqlService = new MysqlService();
  }

  /**
   * Get all settings.
   */
  function getAll() {
    return json_encode($this->mysqlService->findOne('*', 'settings', '`id` = 1'), JSON_NUMERIC_CHECK);
  }

  /**
   * Save all settings.
   */
  function saveAll($data) {
    $args = [
      'currency' => $data->currency, 'ticker' => $data->ticker,
      'fav1' => $data->fav1, 'fav2' => $data->fav2, 'fav3' => $data->fav3, 'fav4' => $data->fav4
    ];
    if ($this->mysqlService->count('*', 'settings') == 0) {
      $columns = 'currency, ticker, fav1, fav2, fav3, fav4';
      $values = ':currency, :ticker, :fav1, :fav2, :fav3, :fav4';
      $this->mysqlService->insert('settings', $columns, $values, $args);
    } else {
      $columns = 'currency = :currency, ticker = :ticker, fav1 = :fav1, fav2 = :fav2, fav3 = :fav3, fav4 = :fav4';
      $this->mysqlService->update('settings', $columns, '`id` = 1', $args);
    }
  }
}
?>
