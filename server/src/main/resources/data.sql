-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: mysql
-- Erstellungszeit: 03. Aug 2021 um 18:41
-- Server-Version: 5.7.23
-- PHP-Version: 7.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

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
-- Daten für Tabelle `type_fiat`
--

INSERT INTO `type_fiat` (`id`, `name`) VALUES
(1, 'EUR'),
(2, 'USD'),
(3, 'CHF'),
(4, 'GBP'),
(8, 'TRY');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
