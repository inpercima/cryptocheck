<?php
require_once 'core.service.php';

class TradesService {

  /**
   * constructor
   */
  function __construct() {}

  /**
   * Get information of the trades.
   */
  function getTrades() {
    $coreService = new CoreService();
    $response = file_get_contents('https://api.bitpanda.com/v1/trades?page_size=500', false, $coreService->createContext());
    header('Content-type: application/json');
    return $response;
  }
}
?>
