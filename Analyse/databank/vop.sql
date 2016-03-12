SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;


CREATE TABLE IF NOT EXISTS `metingen` (
  `timestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reistijd` int(11) NOT NULL,
  `optimal` int(11) NOT NULL,
  `traject_id` int(11) NOT NULL,
  `omgekeerd` tinyint(1) NOT NULL,
  `provider_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `providers` (
`id` int(11) NOT NULL,
  `naam` varchar(100) NOT NULL,
  `is_active` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

CREATE TABLE IF NOT EXISTS `trajecten` (
`id` int(11) NOT NULL,
  `naam` varchar(100) NOT NULL,
  `omgekeerde_naam` varchar(100) NOT NULL,
  `van` varchar(50) NOT NULL,
  `naar` varchar(50) NOT NULL,
  `lengte` int(11) DEFAULT NULL,
  `omgekeerde_lengte` int(11) DEFAULT NULL,
  `optimale_reistijd` int(11) DEFAULT NULL,
  `omgekeerde_optimale_reistijd` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=18 ;

CREATE TABLE IF NOT EXISTS `waypoints` (
  `volgnr` int(11) NOT NULL,
  `traject_id` int(11) NOT NULL,
  `omgekeerd` tinyint(1) NOT NULL,
  `latitude` varchar(45) NOT NULL,
  `longitude` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `metingen`
 ADD PRIMARY KEY (`timestamp`,`traject_id`,`omgekeerd`,`provider_id`), ADD KEY `fk_Meting_Traject_idx` (`traject_id`), ADD KEY `fk_Meting_Provider1_idx` (`provider_id`);

ALTER TABLE `providers`
 ADD PRIMARY KEY (`id`);

ALTER TABLE `trajecten`
 ADD PRIMARY KEY (`id`);

ALTER TABLE `waypoints`
 ADD PRIMARY KEY (`volgnr`,`traject_id`,`omgekeerd`), ADD KEY `fk_waypoints_1_idx` (`traject_id`);


ALTER TABLE `providers`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
ALTER TABLE `trajecten`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=18;

ALTER TABLE `metingen`
ADD CONSTRAINT `fk_Meting_Provider1` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_Meting_Traject` FOREIGN KEY (`traject_id`) REFERENCES `trajecten` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `waypoints`
ADD CONSTRAINT `fk_waypoints_1` FOREIGN KEY (`traject_id`) REFERENCES `trajecten` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
