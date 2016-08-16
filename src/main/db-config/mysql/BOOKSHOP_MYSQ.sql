/* creazione del database */
CREATE DATABASE IF NOT EXISTS `bookshop` DEFAULT CHARACTER SET utf8;

/* creazione utente applicativo*/
CREATE USER 'bookshop'@'localhost' IDENTIFIED BY 'bookshop';
GRANT ALL PRIVILEGES ON bookshop.* TO 'bookshop'@'localhost';

CREATE USER 'bookshop'@'%' IDENTIFIED BY 'bookshop';
GRANT ALL PRIVILEGES ON bookshop.* TO 'bookshop'@'localhost';

