-- phpMyAdmin SQL Dump
-- version 4.0.4.2
-- http://www.phpmyadmin.net
--
-- Machine: localhost
-- Genereertijd: 03 mei 2016 om 16:38
-- Serverversie: 5.6.13
-- PHP-versie: 5.4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databank: `vop`
--
CREATE DATABASE IF NOT EXISTS `vop` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `vop`;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `metingen`
--

CREATE TABLE IF NOT EXISTS `metingen` (
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reistijd` int(11) DEFAULT NULL,
  `traject_id` int(11) NOT NULL DEFAULT '0',
  `provider_id` int(11) NOT NULL,
  PRIMARY KEY (`timestamp`,`traject_id`,`provider_id`),
  KEY `fk_Meting_Provider1_idx` (`provider_id`),
  KEY `fk_Meting_Traject_idx` (`traject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `optimale_reistijden`
--

CREATE TABLE IF NOT EXISTS `optimale_reistijden` (
  `traject_id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL,
  `reistijd` int(11) DEFAULT NULL,
  PRIMARY KEY (`traject_id`,`provider_id`),
  KEY `fk_OptimaleReistijd_Provider_idx` (`provider_id`),
  KEY `fk_OptimaleReistijd_Traject_idx` (`traject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `providers`
--

CREATE TABLE IF NOT EXISTS `providers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naam` varchar(100) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `trafficincidents`
--

CREATE TABLE IF NOT EXISTS `trafficincidents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_id` int(11) NOT NULL,
  `traject_id` int(11) NOT NULL,
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `problem` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `provider_id` (`provider_id`),
  KEY `traject_id` (`traject_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `trajecten`
--

CREATE TABLE IF NOT EXISTS `trajecten` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naam` varchar(100) DEFAULT NULL,
  `lengte` int(11) DEFAULT NULL,
  `optimale_reistijd` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `start_latitude` varchar(45) DEFAULT NULL,
  `start_longitude` varchar(45) DEFAULT NULL,
  `end_latitude` varchar(45) DEFAULT NULL,
  `end_longitude` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=35 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `trajectsynoniemen`
--

CREATE TABLE IF NOT EXISTS `trajectsynoniemen` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naam` varchar(100) NOT NULL,
  `traject_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `traject_id_idx` (`traject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `sessionID` int(8) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `waypoints`
--

CREATE TABLE IF NOT EXISTS `waypoints` (
  `volgnr` int(11) NOT NULL,
  `traject_id` int(11) NOT NULL,
  `latitude` varchar(45) DEFAULT NULL,
  `longitude` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`volgnr`,`traject_id`),
  KEY `fk_waypoints_1_idx` (`traject_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Beperkingen voor gedumpte tabellen
--

--
-- Beperkingen voor tabel `metingen`
--
ALTER TABLE `metingen`
  ADD CONSTRAINT `fk_Meting_Provider1` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Meting_Traject` FOREIGN KEY (`traject_id`) REFERENCES `trajecten` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Beperkingen voor tabel `optimale_reistijden`
--
ALTER TABLE `optimale_reistijden`
  ADD CONSTRAINT `fk_OptimaleReistijd_Provider` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_OptimaleReistijd_Traject` FOREIGN KEY (`traject_id`) REFERENCES `trajecten` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Beperkingen voor tabel `trafficincidents`
--
ALTER TABLE `trafficincidents`
  ADD CONSTRAINT `fk_Incident_Provider` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Incident_Traject` FOREIGN KEY (`traject_id`) REFERENCES `trajecten` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Beperkingen voor tabel `trajectsynoniemen`
--
ALTER TABLE `trajectsynoniemen`
  ADD CONSTRAINT `traject_id` FOREIGN KEY (`traject_id`) REFERENCES `trajecten` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Beperkingen voor tabel `waypoints`
--
ALTER TABLE `waypoints`
  ADD CONSTRAINT `fk_waypoints_1` FOREIGN KEY (`traject_id`) REFERENCES `trajecten` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
