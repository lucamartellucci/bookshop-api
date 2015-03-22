/* creazione del database */
CREATE DATABASE IF NOT EXISTS `library` DEFAULT CHARACTER SET latin1;

/* creazione utente applicativo*/
CREATE USER 'library'@'localhost' IDENTIFIED BY 'library';
--GRANT SELECT, INSERT, UPDATE, DELETE, LOCK TABLES, CREATE TEMPORARY TABLES ON library.* TO 'library'@'localhost';
GRANT ALL PRIVILEGES ON library.* TO 'library'@'localhost';

CREATE USER 'library'@'%' IDENTIFIED BY 'library';
GRANT SELECT, INSERT, UPDATE, DELETE, LOCK TABLES, CREATE TEMPORARY TABLES ON library.* TO 'library'@'%';

