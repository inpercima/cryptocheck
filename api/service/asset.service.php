<?php
require_once 'mysql.service.php';

class AssetService {

  private $mysqlService;

  /**
   * constructor
   */
  function __construct() {
    $this->mysqlService = new MysqlService();
  }

  /**
   * Get information of the assets.
   */
  function getAssets() {
    return json_encode(array_column($this->mysqlService->findAll('`name`', 'type_asset', '`id` > 0 ORDER BY `name` ASC'), 'name'));
  }
}
?>
