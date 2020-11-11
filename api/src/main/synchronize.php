<?php
require_once 'synchronize.service.php';

$synchronizeService = new SynchronizeService();
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Headers: Content-Type");


echo $synchronizeService->saveAll();
?>
