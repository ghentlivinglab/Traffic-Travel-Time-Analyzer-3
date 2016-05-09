SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;


CREATE TABLE IF NOT EXISTS `metingen` (
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `reistijd` int(11) DEFAULT NULL,
  `traject_id` int(11) NOT NULL DEFAULT '0',
  `provider_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `optimale_reistijden` (
  `traject_id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL,
  `reistijd` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `providers` (
`id` int(11) NOT NULL,
  `naam` varchar(100) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

CREATE TABLE IF NOT EXISTS `trafficincidents` (
`id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL,
  `traject_id` int(11) NOT NULL,
  `starttime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `endtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `problem` varchar(255) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

CREATE TABLE IF NOT EXISTS `trajecten` (
`id` int(11) NOT NULL,
  `naam` varchar(100) DEFAULT NULL,
  `lengte` int(11) DEFAULT NULL,
  `optimale_reistijd` int(11) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `start_latitude` varchar(45) DEFAULT NULL,
  `start_longitude` varchar(45) DEFAULT NULL,
  `end_latitude` varchar(45) DEFAULT NULL,
  `end_longitude` varchar(45) DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=35 ;

CREATE TABLE IF NOT EXISTS `trajectsynoniemen` (
`id` int(11) NOT NULL,
  `naam` varchar(100) NOT NULL,
  `traject_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

CREATE TABLE IF NOT EXISTS `users` (
`id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `sessionID` int(8) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

CREATE TABLE IF NOT EXISTS `waypoints` (
  `volgnr` int(11) NOT NULL,
  `traject_id` int(11) NOT NULL,
  `latitude` varchar(45) DEFAULT NULL,
  `longitude` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `metingen`
 ADD PRIMARY KEY (`timestamp`,`traject_id`,`provider_id`), ADD KEY `fk_Meting_Provider1_idx` (`provider_id`), ADD KEY `fk_Meting_Traject_idx` (`traject_id`);

ALTER TABLE `optimale_reistijden`
 ADD PRIMARY KEY (`traject_id`,`provider_id`), ADD KEY `fk_OptimaleReistijd_Provider_idx` (`provider_id`), ADD KEY `fk_OptimaleReistijd_Traject_idx` (`traject_id`);

ALTER TABLE `providers`
 ADD PRIMARY KEY (`id`);

ALTER TABLE `trafficincidents`
 ADD PRIMARY KEY (`id`), ADD KEY `provider_id` (`provider_id`), ADD KEY `traject_id` (`traject_id`);

ALTER TABLE `trajecten`
 ADD PRIMARY KEY (`id`);

ALTER TABLE `trajectsynoniemen`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id_UNIQUE` (`id`), ADD KEY `traject_id_idx` (`traject_id`);

ALTER TABLE `users`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `username` (`username`), ADD KEY `id` (`id`);

ALTER TABLE `waypoints`
 ADD PRIMARY KEY (`volgnr`,`traject_id`), ADD KEY `fk_waypoints_1_idx` (`traject_id`);


ALTER TABLE `providers`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
ALTER TABLE `trafficincidents`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
ALTER TABLE `trajecten`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=35;
ALTER TABLE `trajectsynoniemen`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `users`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;

ALTER TABLE `metingen`
ADD CONSTRAINT `fk_Meting_Provider1` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_Meting_Traject` FOREIGN KEY (`traject_id`) REFERENCES `trajecten` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `optimale_reistijden`
ADD CONSTRAINT `fk_OptimaleReistijd_Provider` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_OptimaleReistijd_Traject` FOREIGN KEY (`traject_id`) REFERENCES `trajecten` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `trafficincidents`
ADD CONSTRAINT `fk_Incident_Provider` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
ADD CONSTRAINT `fk_Incident_Traject` FOREIGN KEY (`traject_id`) REFERENCES `trajecten` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `trajectsynoniemen`
ADD CONSTRAINT `traject_id` FOREIGN KEY (`traject_id`) REFERENCES `trajecten` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;

ALTER TABLE `waypoints`
ADD CONSTRAINT `fk_waypoints_1` FOREIGN KEY (`traject_id`) REFERENCES `trajecten` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
