<?php
require_once 'config.php';

class MysqlService {

  /**
   * constructor
   */
  function __construct() {}

  /**
   * connect with the database
   */
  function connect() {
    try {
      $pdo = new PDO('mysql:host='.Config::DB_HOST.';dbname='.Config::DB_NAME.';charset=utf8', Config::DB_USER, Config::DB_PASS);
    } catch (PDOException $e) {
      echo 'Error: ' . $e->getMessage();
      exit();
    }
    return $pdo;
  }
}
?>
