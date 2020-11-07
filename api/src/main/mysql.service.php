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
      $pdo = new PDO('mysql:host='.CONFIG::DB_HOST.';dbname='.CONFIG::DB_NAME.';charset=utf8', CONFIG::DB_USER, CONFIG::DB_PASS);
    } catch (PDOException $e) {
      echo 'Error: ' . $e->getMessage();
      exit();
    }
    return $pdo;
  }
}
?>
