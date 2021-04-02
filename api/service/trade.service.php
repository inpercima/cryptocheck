<?php
require_once 'core.service.php';

class TradeService {

  private $coreService;

  /**
   * constructor
   */
  function __construct() {
    $this->coreService = new CoreService();
  }

  /**
   * Get information of the trades.
   */
  function getTrades() {
    return file_get_contents('https://api.bitpanda.com/v1/trades?page_size=500', false, $this->coreService->createContext());
  }
}
?>
