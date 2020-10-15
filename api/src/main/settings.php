<?php
require_once 'settings.service.php';

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
