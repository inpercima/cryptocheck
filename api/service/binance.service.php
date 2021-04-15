<?php
require_once 'core.service.php';
require_once 'mysql.service.php';

class BinanceService {

  private $coreService;

  private $mysqlService;

  /**
   * constructor
   */
  function __construct() {
    $this->coreService = new CoreService();
    $this->mysqlService = new MysqlService();
  }

  function getInfo() {
    $params['timestamp'] = time() * 1000;
    $params['symbol'] = 'DOGEUSDT';
    $query = http_build_query($params, '', '&');
    $signature = hash_hmac('sha256', $query, CONFIG::API_SECRET);
    $endpoint = "https://api.binance.com/api/v3/allOrders?{$query}&signature={$signature}";
    return file_get_contents($endpoint, false, $this->coreService->createContext('X-MBX-APIKEY'));
  }
}
?>
