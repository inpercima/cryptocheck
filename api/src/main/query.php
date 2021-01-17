<?php

class Query {

  /**
   * constructor
   */
  public function __construct() {}

  var $ASSET_GET_NAME = 'SELECT `name` FROM `asset` WHERE `id` > 0  ORDER BY `name` ASC';

  const PROFIT_LOSS_PER_SELL = <<<SQL
    SELECT `t1`.`asset_id`, `t1`.`amount_fiat` AS `buy`, `t2`.`amount_fiat` AS `sell`, `t1`.`amount_coin`, ROUND(`t2`.`amount_fiat` - SUM(`t1`.`amount_fiat`), 2) AS `total`
    FROM `transaction` AS `t1`
    JOIN `transaction` AS `t2`
    ON `t1`.`ref_transaction_id` = `t2`.`transaction_id`
    GROUP BY `sell`;
SQL;

  const PROFIT_LOSS_PER_SELL_TOTAL = <<<SQL
    SELECT COALESCE(SUM(`assets`.`total`), 0)
    FROM (
      SELECT `t1`.`asset_id`, `t1`.`amount_fiat` AS `buy`, `t2`.`amount_fiat` AS `sell`, `t1`.`amount_coin`, ROUND(`t2`.`amount_fiat` - SUM(`t1`.`amount_fiat`), 2) AS `total`
      FROM `transaction` AS `t1`
      JOIN `transaction` AS `t2`
      ON `t1`.`ref_transaction_id` = `t2`.`transaction_id`
      GROUP BY `sell`
    ) AS `assets`
SQL;

  const PROFIT_LOSS_WITH_FEE = <<<SQL
    SELECT (
      (
        SELECT COALESCE(SUM(`assets`.`total`), 0)
        FROM (
          SELECT `t1`.`asset_id`, `t1`.`amount_fiat` AS `buy`, `t2`.`amount_fiat` AS `sell`, `t1`.`amount_coin`, ROUND(`t2`.`amount_fiat` - SUM(`t1`.`amount_fiat`), 2) AS `total`
          FROM `transaction` AS `t1`
          JOIN `transaction` AS `t2`
          ON `t1`.`ref_transaction_id` = `t2`.`transaction_id`
          GROUP BY `sell`
        ) AS `assets`
      )
      -
      (
        SELECT COALESCE(SUM(`amount_fiat`), 0)
        FROM `transaction`
        WHERE `asset_id` = 33
        AND `type` = 'withdrawal'
      )
    ) AS `total`
SQL;
}
?>
