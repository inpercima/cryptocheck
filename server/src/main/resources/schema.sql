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
-- Datenbank: `cryptocheck`
--
CREATE DATABASE IF NOT EXISTS `cryptocheck` DEFAULT CHARACTER SET utf8 COLLATE utf8mb4_general_ci;
USE `cryptocheck`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `type_asset`
--

CREATE TABLE `type_asset` (
  `id` int(3) NOT NULL,
  `name` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `type_fiat`
--

CREATE TABLE `type_fiat` (
  `id` int(3) NOT NULL,
  `name` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indizes der exportierten Tabellen
--

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
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
