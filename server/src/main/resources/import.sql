-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: mysql
-- Erstellungszeit: 05. Aug 2021 um 12:26
-- Server-Version: 8.0.22
-- PHP-Version: 7.4.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Daten für Tabelle `asset_type`
--

INSERT INTO `asset_type` (`id`, `name`) VALUES (1, 'BTC');
INSERT INTO `asset_type` (`id`, `name`) VALUES (3, 'LTC');
INSERT INTO `asset_type` (`id`, `name`) VALUES (5, 'ETH');
INSERT INTO `asset_type` (`id`, `name`) VALUES (6, 'LSK');
INSERT INTO `asset_type` (`id`, `name`) VALUES (7, 'DASH');
INSERT INTO `asset_type` (`id`, `name`) VALUES (8, 'XRP');
INSERT INTO `asset_type` (`id`, `name`) VALUES (9, 'BCH');
INSERT INTO `asset_type` (`id`, `name`) VALUES (11, 'PAN');
INSERT INTO `asset_type` (`id`, `name`) VALUES (12, 'KMD');
INSERT INTO `asset_type` (`id`, `name`) VALUES (13, 'MIOTA');
INSERT INTO `asset_type` (`id`, `name`) VALUES (14, 'EOS');
INSERT INTO `asset_type` (`id`, `name`) VALUES (15, 'OMG');
INSERT INTO `asset_type` (`id`, `name`) VALUES (16, 'REP');
INSERT INTO `asset_type` (`id`, `name`) VALUES (17, 'ZRX');
INSERT INTO `asset_type` (`id`, `name`) VALUES (18, 'ZEC');
INSERT INTO `asset_type` (`id`, `name`) VALUES (19, 'XEM');
INSERT INTO `asset_type` (`id`, `name`) VALUES (20, 'XLM');
INSERT INTO `asset_type` (`id`, `name`) VALUES (21, 'XTZ');
INSERT INTO `asset_type` (`id`, `name`) VALUES (22, 'ADA');
INSERT INTO `asset_type` (`id`, `name`) VALUES (23, 'NEO');
INSERT INTO `asset_type` (`id`, `name`) VALUES (24, 'ETC');
INSERT INTO `asset_type` (`id`, `name`) VALUES (25, 'LINK');
INSERT INTO `asset_type` (`id`, `name`) VALUES (26, 'WAVES');
INSERT INTO `asset_type` (`id`, `name`) VALUES (27, 'USDT');
INSERT INTO `asset_type` (`id`, `name`) VALUES (30, 'USDC');
INSERT INTO `asset_type` (`id`, `name`) VALUES (31, 'TRX');
INSERT INTO `asset_type` (`id`, `name`) VALUES (32, 'ATOM');
INSERT INTO `asset_type` (`id`, `name`) VALUES (33, 'BEST');
INSERT INTO `asset_type` (`id`, `name`) VALUES (34, 'BAT');
INSERT INTO `asset_type` (`id`, `name`) VALUES (37, 'CHZ');
INSERT INTO `asset_type` (`id`, `name`) VALUES (38, 'DOGE');
INSERT INTO `asset_type` (`id`, `name`) VALUES (39, 'ONT');
INSERT INTO `asset_type` (`id`, `name`) VALUES (43, 'QTUM');
INSERT INTO `asset_type` (`id`, `name`) VALUES (44, 'VET');
INSERT INTO `asset_type` (`id`, `name`) VALUES (51, 'DOT');
INSERT INTO `asset_type` (`id`, `name`) VALUES (52, 'YFI');
INSERT INTO `asset_type` (`id`, `name`) VALUES (53, 'MKR');
INSERT INTO `asset_type` (`id`, `name`) VALUES (54, 'COMP');
INSERT INTO `asset_type` (`id`, `name`) VALUES (55, 'SNX');
INSERT INTO `asset_type` (`id`, `name`) VALUES (56, 'UNI');
INSERT INTO `asset_type` (`id`, `name`) VALUES (57, 'FIL');
INSERT INTO `asset_type` (`id`, `name`) VALUES (58, 'AAVE');
INSERT INTO `asset_type` (`id`, `name`) VALUES (59, 'KNC');
INSERT INTO `asset_type` (`id`, `name`) VALUES (60, 'BAND');
INSERT INTO `asset_type` (`id`, `name`) VALUES (61, 'REN');
INSERT INTO `asset_type` (`id`, `name`) VALUES (63, 'UMA');
INSERT INTO `asset_type` (`id`, `name`) VALUES (65, 'THETA');
INSERT INTO `asset_type` (`id`, `name`) VALUES (66, 'OCEAN');
INSERT INTO `asset_type` (`id`, `name`) VALUES (67, 'AVAX');
INSERT INTO `asset_type` (`id`, `name`) VALUES (68, 'ALGO');
INSERT INTO `asset_type` (`id`, `name`) VALUES (69, 'ANT');
INSERT INTO `asset_type` (`id`, `name`) VALUES (70, 'BTT');
INSERT INTO `asset_type` (`id`, `name`) VALUES (71, 'DGB');
INSERT INTO `asset_type` (`id`, `name`) VALUES (129, '1INCH');
INSERT INTO `asset_type` (`id`, `name`) VALUES (130, 'EGLD');
INSERT INTO `asset_type` (`id`, `name`) VALUES (132, 'IOST');
INSERT INTO `asset_type` (`id`, `name`) VALUES (133, 'LUNA');
INSERT INTO `asset_type` (`id`, `name`) VALUES (134, 'MATIC');
INSERT INTO `asset_type` (`id`, `name`) VALUES (135, 'SOL');
INSERT INTO `asset_type` (`id`, `name`) VALUES (136, 'ICP');
INSERT INTO `asset_type` (`id`, `name`) VALUES (138, 'MANA');
INSERT INTO `asset_type` (`id`, `name`) VALUES (139, 'CAKE');
INSERT INTO `asset_type` (`id`, `name`) VALUES (140, 'KSM');
INSERT INTO `asset_type` (`id`, `name`) VALUES (141, 'SUSHI');
INSERT INTO `asset_type` (`id`, `name`) VALUES (142, 'KLAY');

-- --------------------------------------------------------

--
-- Daten für Tabelle `fiat_type`
--

INSERT INTO `fiat_type` (`id`, `name`) VALUES (1, 'EUR');
INSERT INTO `fiat_type` (`id`, `name`) VALUES (2, 'USD');
INSERT INTO `fiat_type` (`id`, `name`) VALUES (3, 'CHF');
INSERT INTO `fiat_type` (`id`, `name`) VALUES (4, 'GBP');
INSERT INTO `fiat_type` (`id`, `name`) VALUES (8, 'TRY');

-- --------------------------------------------------------

--
-- Daten für Tabelle `origin`
--

INSERT INTO `origin` (`id`, `name`) VALUES (1, 'Bitpanda');
INSERT INTO `origin` (`id`, `name`) VALUES (2, 'Bitcoin.de');
INSERT INTO `origin` (`id`, `name`) VALUES (3, 'Binance');
INSERT INTO `origin` (`id`, `name`) VALUES (4, 'Kucoin');

-- --------------------------------------------------------

--
-- Daten für Tabelle `hibernate_sequence`
--

UPDATE`hibernate_sequence` SET `next_val` = 5;

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
