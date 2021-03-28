<?php
require_once '../service/core.service.php';
$coreService = new CoreService();

require_once $coreService->requireConfig();

class MysqlService {

  private $pdo;

  /**
   * constructor
   */
  function __construct() {
    try {
      $this->pdo = new PDO('mysql:host='.CONFIG::DB_HOST.';dbname='.CONFIG::DB_NAME.';charset=utf8', CONFIG::DB_USER, CONFIG::DB_PASS);
    } catch (PDOException $e) {
      echo 'Error: ' . $e->getMessage();
      exit();
    }
  }

  function findOne($columns, $table, $where = null, $args = null) {
    return $this->find($columns, $table, $where, $args)->fetch(PDO::FETCH_ASSOC);
  }

  function findAll($columns, $table, $where = null, $args = null) {
    return $this->find($columns, $table, $where, $args)->fetchAll(PDO::FETCH_ASSOC);
  }

  function count($columns, $table, $where = null, $args = null) {
    return $this->find("COUNT({$columns})", $table, $where, $args)->fetchColumn();
  }

  function insert($table, $columns, $values, $args = null) {
    $prefix = CONFIG::DB_PREFIX;
    return $this->run("INSERT INTO `{$prefix}{$table}` ({$columns}) VALUES ({$values})", null, $args);
  }

  function update($table, $columns, $where = null, $args = null) {
    $prefix = CONFIG::DB_PREFIX;
    return $this->run("UPDATE `{$prefix}{$table}` SET {$columns}", $where, $args);
  }

  function find($columns, $table, $where, $args) {
    $prefix = CONFIG::DB_PREFIX;
    return $this->run("SELECT {$columns} FROM `{$prefix}{$table}`", $where, $args);
  }

  function buildQuery($query, $where) {
    if (isset($where)) {
      $query .= ' WHERE ' . $where;
    }
    return $query;
  }

  function run($query, $where, $args) {
    $buildQuery = $this->buildQuery($query, $where);
    $stmt;
    if (isset($args)) {
      $stmt = $this->pdo->prepare($buildQuery);
      $stmt->execute($args);
    } else {
      $stmt = $this->pdo->query($buildQuery);
    }
    return $stmt;
  }
}
?>
