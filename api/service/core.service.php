<?php

class CoreService {

  /**
   * constructor
   */
  public function __construct() {}

  public function requireConfig() {
    $mode = getenv('PROFILE') ?: 'dev';
    return "../config/config.${mode}.php";
  }

  public function setHeader() {
    header('Access-Control-Allow-Origin: *');
    header('Content-Type: application/json; charset=UTF-8');
    header('Access-Control-Allow-Headers: Access-Control-Allow-Origin, Content-Type');
  }

  public function getParams() {
    $query = $_SERVER['QUERY_STRING'];
    parse_str($query, $queryArr);
    return $queryArr;
  }

  public function getParam($param) {
    $params = $this->getParams();
    return $params[$param];
  }

  /**
   * Create a context for getting data.
   */
  public function createContext($headerApiKey = 'X-API-KEY') {
    return stream_context_create(array(
      'http' => array(
        'header' => array(
          $headerApiKey . ': ' . Config::API_KEY
        )
      )
    ));
  }

  public function getInput() {
    return json_decode(file_get_contents('php://input'));
  }

  public function pathInfo($path = null) {
    $result = null;
    if (isset($_SERVER['PATH_INFO'])) {
      $pathInfo = substr($_SERVER['PATH_INFO'], 1);
      $result = !isset($path) ? $pathInfo : ($pathInfo == $path ? true : false);
    }
    return $result;
  }
}
?>
