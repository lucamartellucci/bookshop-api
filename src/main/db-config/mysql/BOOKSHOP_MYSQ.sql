/* creazione del database */
CREATE DATABASE IF NOT EXISTS `bookshop` DEFAULT CHARACTER SET latin1;

/* creazione utente applicativo*/
CREATE USER 'bookshop'@'localhost' IDENTIFIED BY 'bookshop';
--GRANT SELECT, INSERT, UPDATE, DELETE, LOCK TABLES, CREATE TEMPORARY TABLES ON bookshop.* TO 'bookshop'@'localhost';
GRANT ALL PRIVILEGES ON bookshop.* TO 'bookshop'@'localhost';

CREATE USER 'bookshop'@'%' IDENTIFIED BY 'bookshop';
GRANT SELECT, INSERT, UPDATE, DELETE, LOCK TABLES, CREATE TEMPORARY TABLES ON bookshop.* TO 'bookshop'@'%';

