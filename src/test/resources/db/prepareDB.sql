INSERT INTO user(id,email,username,password,birthdate,gender,roles) values (1,'luca.martellucci@gmail.com', 'luca', 'password01', '1978-09-25', 'M', 'ADMIN,USER' );
INSERT INTO user(id,email,username,password,birthdate,gender,roles) values (2,'erica.cassina@gmail.com', 'erica', 'password01', '1982-03-02', 'F', 'ADMIN,USER');

INSERT INTO book(id,title,isbn,description,year,currency,price) values (1,'title_1','11111111111','description_1',1978,'EUR',12.63);
INSERT INTO book(id,title,isbn,description,year,currency,price) values (2,'title_2','22222222222','description_2',2011,'EUR',8.00);
INSERT INTO book(id,title,isbn,description,year,currency,price) values (3,'title_3','33333333333','description_3',2002,'EUR',15.63);


INSERT INTO author(id,name,surname,birth_place,born,died) values (1,'name_1','surname_1','place_1','1978-09-25','2070-02-22');
INSERT INTO author(id,name,surname,birth_place,born,died) values (2,'name_2','surname_2','place_2','1878-03-22','1967-03-21');

INSERT INTO authored_by(author_id, book_id) values (1,1);
INSERT INTO authored_by(author_id, book_id) values (2,1);
INSERT INTO authored_by(author_id, book_id) values (2,2);