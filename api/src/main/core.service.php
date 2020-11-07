<?php
require_once 'config.php';

class CoreService {

  /**
   * constructor
   */
  function __construct() {}

  /**
   * Create context for getting data.
   */
  function createContext() {
    return stream_context_create(array(
      'http' => array(
        'header' => array(
          'X-API-KEY: ' . Config::API_KEY
        )
      )
    ));
  }
}
?>
