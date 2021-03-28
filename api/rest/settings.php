<?php
require_once '../service/core.service.php';
$coreService = new CoreService();

require_once $coreService->requireConfig();
require_once '../service/settings.service.php';

$coreService->setHeader();

$settingsService = new SettingsService();
switch ($_SERVER['REQUEST_METHOD']) {
  case 'GET':
    echo $settingsService->getAll();
    break;
  case 'POST':
    echo $settingsService->saveAll(json_decode(file_get_contents('php://input')));
    break;
  default:
    break;
}
?>
