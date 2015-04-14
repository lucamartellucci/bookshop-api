/* creazione del database */
CREATE DATABASE IF NOT EXISTS `bookshop_test` DEFAULT CHARACTER SET latin1;


/* creazione utente applicativo*/
CREATE USER 'bookshop_test'@'localhost' IDENTIFIED BY 'bookshop';

GRANT ALL PRIVILEGES ON bookshop_test.* TO 'bookshop_test'@'localhost';