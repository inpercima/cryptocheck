<?php
require_once 'mysql.service.php';
require_once 'query.php';

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Headers: Content-Type");

$mysqlService = new MysqlService();
$pdo = $mysqlService->connect();

$stmt = $pdo->prepare(Query::PROFIT_LOSS_PER_SELL_TOTAL);
$stmt->execute();
$perSales = $stmt->fetchColumn();

$stmt = $pdo->prepare(Query::PROFIT_LOSS_WITH_FEE);
$stmt->execute();
$withFee = $stmt->fetchColumn();

echo json_encode(array('sales' => $perSales, 'salesMinusFee' => $withFee), JSON_NUMERIC_CHECK);
?>
