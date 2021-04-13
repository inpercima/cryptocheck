--
-- Datenbank: `cryptocheck`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `settings`
--

CREATE TABLE `settings` (
  `id` int(1) NOT NULL,
  `currency` enum('EUR','USD') COLLATE utf8mb4_unicode_ci NOT NULL,
  `ticker` enum('CCMP','BPDA') COLLATE utf8mb4_unicode_ci NOT NULL,
  `fav1` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fav2` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fav3` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fav4` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `transaction_asset`
--

CREATE TABLE `transaction_asset` (
  `id` int(5) NOT NULL,
  `type_asset_id` int(3) NOT NULL,
  `date` date NOT NULL,
  `price` decimal(15,8) NOT NULL,
  `amount` decimal(15,8) NOT NULL,
  `number` decimal(15,8) NOT NULL,
  `fee` decimal(15,8) NOT NULL,
  `type` enum('buy','sell','transfer','deposit','withdrawal','refund','ico','payout') COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('pending','processing','unconfirmed_transaction_out','open_invitation','finished','canceled') COLLATE utf8mb4_unicode_ci NOT NULL,
  `transaction_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ref_transaction_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trade_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ref_trade_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `transaction_external`
--

CREATE TABLE `transaction_external` (
  `id` int(5) NOT NULL,
  `type_asset_id` int(3) NOT NULL,
  `date` date NOT NULL,
  `price` decimal(15,8) NOT NULL,
  `amount` decimal(15,8) NOT NULL,
  `number` decimal(15,8) NOT NULL,
  `fee` decimal(15,8) NOT NULL,
  `type` enum('buy','sell','payout') COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `transaction_fiat`
--

CREATE TABLE `transaction_fiat` (
  `id` int(5) NOT NULL,
  `type_fiat_id` int(3) NOT NULL,
  `date` date NOT NULL,
  `amount` decimal(15,8) NOT NULL,
  `fee` decimal(15,8) NOT NULL,
  `type` enum('transfer','deposit','withdrawal','refund') COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('pending','processing','finished','canceled') COLLATE utf8mb4_unicode_ci NOT NULL,
  `transaction_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `type_asset`
--

CREATE TABLE `type_asset` (
  `id` int(3) NOT NULL,
  `name` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Daten für Tabelle `type_asset`
--

INSERT INTO `type_asset` (`id`, `name`) VALUES
(1, 'BTC'),
(3, 'LTC'),
(5, 'ETH'),
(6, 'LSK'),
(7, 'DASH'),
(8, 'XRP'),
(9, 'BCH'),
(11, 'PAN'),
(12, 'KMD'),
(13, 'MIOTA'),
(14, 'EOS'),
(15, 'OMG'),
(16, 'REP'),
(17, 'ZRX'),
(18, 'ZEC'),
(19, 'XEM'),
(20, 'XLM'),
(21, 'XTZ'),
(22, 'ADA'),
(23, 'NEO'),
(24, 'ETC'),
(25, 'LINK'),
(26, 'WAVES'),
(27, 'USDT'),
(30, 'USDC'),
(31, 'TRX'),
(32, 'ATOM'),
(33, 'BEST'),
(34, 'BAT'),
(37, 'CHZ'),
(38, 'DOGE'),
(39, 'ONT'),
(43, 'QTUM'),
(44, 'VET'),
(51, 'DOT'),
(52, 'YFI'),
(53, 'MKR'),
(54, 'COMP'),
(55, 'SNX'),
(56, 'UNI'),
(57, 'FIL'),
(58, 'AAVE'),
(59, 'KNC'),
(60, 'BAND'),
(61, 'REN'),
(63, 'UMA'),
(66, 'OCEAN'),
(67, 'AVAX'),
(68, 'ALGO'),
(69, 'ANT'),
(70, 'BTT'),
(71, 'DGB');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `type_fiat`
--

CREATE TABLE `type_fiat` (
  `id` int(3) NOT NULL,
  `name` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Daten für Tabelle `type_fiat`
--

INSERT INTO `type_fiat` (`id`, `name`) VALUES
(1, 'EUR'),
(2, 'USD'),
(3, 'CHF'),
(4, 'GBP'),
(8, 'TRY');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `transaction_asset`
--
ALTER TABLE `transaction_asset`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_ta_type_asset_id` (`type_asset_id`);

--
-- Indizes für die Tabelle `transaction_external`
--
ALTER TABLE `transaction_external`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_te_type_asset_id` (`type_asset_id`);

--
-- Indizes für die Tabelle `transaction_fiat`
--
ALTER TABLE `transaction_fiat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_tf_type_fiat_id` (`type_fiat_id`) USING BTREE;

--
-- Indizes für die Tabelle `type_asset`
--
ALTER TABLE `type_asset`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `type_fiat`
--
ALTER TABLE `type_fiat`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `transaction_asset`
--
ALTER TABLE `transaction_asset`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `transaction_external`
--
ALTER TABLE `transaction_external`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `transaction_fiat`
--
ALTER TABLE `transaction_fiat`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `transaction_asset`
--
ALTER TABLE `transaction_asset`
  ADD CONSTRAINT `fk_ta_type_asset_id` FOREIGN KEY (`type_asset_id`) REFERENCES `type_asset` (`id`);

--
-- Constraints der Tabelle `transaction_external`
--
ALTER TABLE `transaction_external`
  ADD CONSTRAINT `fk_te_type_asset_id` FOREIGN KEY (`type_asset_id`) REFERENCES `type_asset` (`id`);

--
-- Constraints der Tabelle `transaction_fiat`
--
ALTER TABLE `transaction_fiat`
  ADD CONSTRAINT `fk_tf_type_fiat_id` FOREIGN KEY (`type_fiat_id`) REFERENCES `type_fiat` (`id`);
COMMIT;
