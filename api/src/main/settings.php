<?php
require_once 'settings.service.php';

$settingsService = new SettingsService();
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Headers: Content-Type");

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
