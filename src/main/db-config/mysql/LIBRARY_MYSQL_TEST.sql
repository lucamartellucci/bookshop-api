/* creazione del database */
CREATE DATABASE IF NOT EXISTS `library_test` DEFAULT CHARACTER SET latin1;


/* creazione utente applicativo*/
CREATE USER 'library_test'@'localhost' IDENTIFIED BY 'library';

GRANT ALL PRIVILEGES ON library_test.* TO 'library_test'@'localhost';