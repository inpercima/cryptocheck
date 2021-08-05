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
-- Daten für Tabelle `type_asset`
--

INSERT INTO `type_asset` (`id`, `name`) VALUES (1, 'BTC');
INSERT INTO `type_asset` (`id`, `name`) VALUES (3, 'LTC');
INSERT INTO `type_asset` (`id`, `name`) VALUES (5, 'ETH');
INSERT INTO `type_asset` (`id`, `name`) VALUES (6, 'LSK');
INSERT INTO `type_asset` (`id`, `name`) VALUES (7, 'DASH');
INSERT INTO `type_asset` (`id`, `name`) VALUES (8, 'XRP');
INSERT INTO `type_asset` (`id`, `name`) VALUES (9, 'BCH');
INSERT INTO `type_asset` (`id`, `name`) VALUES (11, 'PAN');
INSERT INTO `type_asset` (`id`, `name`) VALUES (12, 'KMD');
INSERT INTO `type_asset` (`id`, `name`) VALUES (13, 'MIOTA');
INSERT INTO `type_asset` (`id`, `name`) VALUES (14, 'EOS');
INSERT INTO `type_asset` (`id`, `name`) VALUES (15, 'OMG');
INSERT INTO `type_asset` (`id`, `name`) VALUES (16, 'REP');
INSERT INTO `type_asset` (`id`, `name`) VALUES (17, 'ZRX');
INSERT INTO `type_asset` (`id`, `name`) VALUES (18, 'ZEC');
INSERT INTO `type_asset` (`id`, `name`) VALUES (19, 'XEM');
INSERT INTO `type_asset` (`id`, `name`) VALUES (20, 'XLM');
INSERT INTO `type_asset` (`id`, `name`) VALUES (21, 'XTZ');
INSERT INTO `type_asset` (`id`, `name`) VALUES (22, 'ADA');
INSERT INTO `type_asset` (`id`, `name`) VALUES (23, 'NEO');
INSERT INTO `type_asset` (`id`, `name`) VALUES (24, 'ETC');
INSERT INTO `type_asset` (`id`, `name`) VALUES (25, 'LINK');
INSERT INTO `type_asset` (`id`, `name`) VALUES (26, 'WAVES');
INSERT INTO `type_asset` (`id`, `name`) VALUES (27, 'USDT');
INSERT INTO `type_asset` (`id`, `name`) VALUES (30, 'USDC');
INSERT INTO `type_asset` (`id`, `name`) VALUES (31, 'TRX');
INSERT INTO `type_asset` (`id`, `name`) VALUES (32, 'ATOM');
INSERT INTO `type_asset` (`id`, `name`) VALUES (33, 'BEST');
INSERT INTO `type_asset` (`id`, `name`) VALUES (34, 'BAT');
INSERT INTO `type_asset` (`id`, `name`) VALUES (37, 'CHZ');
INSERT INTO `type_asset` (`id`, `name`) VALUES (38, 'DOGE');
INSERT INTO `type_asset` (`id`, `name`) VALUES (39, 'ONT');
INSERT INTO `type_asset` (`id`, `name`) VALUES (43, 'QTUM');
INSERT INTO `type_asset` (`id`, `name`) VALUES (44, 'VET');
INSERT INTO `type_asset` (`id`, `name`) VALUES (51, 'DOT');
INSERT INTO `type_asset` (`id`, `name`) VALUES (52, 'YFI');
INSERT INTO `type_asset` (`id`, `name`) VALUES (53, 'MKR');
INSERT INTO `type_asset` (`id`, `name`) VALUES (54, 'COMP');
INSERT INTO `type_asset` (`id`, `name`) VALUES (55, 'SNX');
INSERT INTO `type_asset` (`id`, `name`) VALUES (56, 'UNI');
INSERT INTO `type_asset` (`id`, `name`) VALUES (57, 'FIL');
INSERT INTO `type_asset` (`id`, `name`) VALUES (58, 'AAVE');
INSERT INTO `type_asset` (`id`, `name`) VALUES (59, 'KNC');
INSERT INTO `type_asset` (`id`, `name`) VALUES (60, 'BAND');
INSERT INTO `type_asset` (`id`, `name`) VALUES (61, 'REN');
INSERT INTO `type_asset` (`id`, `name`) VALUES (63, 'UMA');
INSERT INTO `type_asset` (`id`, `name`) VALUES (66, 'OCEAN');
INSERT INTO `type_asset` (`id`, `name`) VALUES (67, 'AVAX');
INSERT INTO `type_asset` (`id`, `name`) VALUES (68, 'ALGO');
INSERT INTO `type_asset` (`id`, `name`) VALUES (69, 'ANT');
INSERT INTO `type_asset` (`id`, `name`) VALUES (70, 'BTT');
INSERT INTO `type_asset` (`id`, `name`) VALUES (71, 'DGB');

-- --------------------------------------------------------

--
-- Daten für Tabelle `type_fiat`
--

INSERT INTO `type_fiat` (`id`, `name`) VALUES (1, 'EUR');
INSERT INTO `type_fiat` (`id`, `name`) VALUES (2, 'USD');
INSERT INTO `type_fiat` (`id`, `name`) VALUES (3, 'CHF');
INSERT INTO `type_fiat` (`id`, `name`) VALUES (4, 'GBP');
INSERT INTO `type_fiat` (`id`, `name`) VALUES (8, 'TRY');

COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
