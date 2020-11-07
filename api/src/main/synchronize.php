<?php
require_once 'synchronize.service.php';

$synchronizeService = new SynchronizeService();

echo $synchronizeService->saveAll();
?>
