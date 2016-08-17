[![Travis](https://img.shields.io/travis/lucamartellucci/bookshop-backend.svg)](https://travis-ci.org/lucamartellucci/bookshop-backend)
[![Coveralls](https://img.shields.io/coveralls/lucamartellucci/bookshop-backend.svg)](https://coveralls.io/repos/lucamartellucci/bookshop-backend/badge.svg?branch=master&service=github)
[![Codacy](https://img.shields.io/codacy/109ed4181f9a4fd59d67ae6dadd362ce.svg)](https://www.codacy.com/app/luca-martellucci/bookshop-backend/dashboard)
[![GitHub license](https://img.shields.io/github/license/lucamartellucci/bookshop-backend.svg)](https://github.com/lucamartellucci/bookshop-backend/blob/master/LICENSE.md)
[![Dependency Status](https://www.versioneye.com/user/projects/57052558fcd19a0039f15cca/badge.svg?style=plastic)](https://www.versioneye.com/user/projects/57052558fcd19a0039f15cca)
BookShop
=============

A simple rest api for a book shop
### Prerequisites
To build and run the application you need:
* jdk 8
* maven 3.x.x
* mysql 5.x.x
* docker (optional)
* docker-compose (optional)
 
The docker objects are optional and are required only if you want build and run the application using docker.

### Build and Run - standard way
To build the application do the following steps:
* setup the database
* build the application with maven
* run the application

#### Database setup
Setup the MySql database using the SQL script available at **bookshop-backend/src/main/db-config/mysql/BOOKSHOP_MYSQ.sql**
The script will create an empty database called **bookshop** and a user called **bookshop**. Open the script for details.

```sh
$ mysql -uroot -p < bookshop-backend/src/main/db-config/mysql/BOOKSHOP_MYSQ.sql
```
#### Maven build
Build the application using Maven
```sh
$ mvn clean package
```

#### Launch the application
The output of the build is a jar file. To run it just use the classical way:

```sh
$ java -jar bookshop.jar 
```
The application, by default, will try to connect to a local instance of MySql. I you prefer to connect to a remote database is possible to use the JDBC_DATABASE_HOSTNAME option.

##### JVM Options
is possible to specify two options:
* logging.file --> specify the location of the logging file
* JDBC_DATABASE_HOSTNAME --> specify the hostname of the database

```sh
$ java -Dlogging.file=logs/bookshop.log -DJDBC_DATABASE_HOSTNAME=127.0.0.1 -jar bookshop.jar 
```

### Build and Run - docker way
Launch the dockerBuildAndRun.sh bash script to build a docker image containing the bookshop spring boot application.
After building the docker image, the script will launch a **docker-compose** up to instantiate two docker container:
* a container with the mysql instance
* a container with the bookshop api


When the containers are up and running the IP of the endpoint of the bookshop api will be showed.
At the moment the docker-compose file use the old docker-compose syntax (**version 1**) 

```sh
$ ./dockerBuildAndRun.sh
```
### Test
To test the api try with a curl
```sh
$ curl --user admin:password01 http://localhost:8080/bookshop/api/books/1

* Connected to localhost port 8080 (#0)
* Server auth using Basic with user 'admin'
> GET /bookshop/api/books/1 HTTP/1.1
> Authorization: Basic YWRtaW46cGFzc3dvcmQwMQ==
> User-Agent: curl/7.35.0
> Host: localhost:8080
> Accept: */*
> 
< HTTP/1.1 200 OK
* Server Apache-Coyote/1.1 is not blacklisted
< Server: Apache-Coyote/1.1
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Wed, 17 Aug 2016 20:52:09 GMT
< 
* Connection #0 to host localhost left intact
{"id":1,"title":"Gomorra","isbn":"1234567890","description":"Gomorra desc","authors":[{"id":1,"name":"Roberto","surname":"Saviano","birthplace":"Napoli","born":"1975-11-02"}],"price":12.0,"releaseDate":"2008-11-02","currency":"EUR"}

```

or if you prefer httpie ( https://github.com/jkbrzt/httpie )
```sh
$ http -v -a luca:password01 GET http://localhost:8080/bookshop/api/books/1

GET /bookshop/api/books/1 HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate, compress
Authorization: Basic bHVjYTpwYXNzd29yZDAx
Host: localhost:8080
User-Agent: HTTPie/0.8.0

HTTP/1.1 200 OK
Cache-Control: no-cache, no-store, max-age=0, must-revalidate
Content-Type: application/json;charset=UTF-8
Date: Wed, 17 Aug 2016 20:50:32 GMT
Expires: 0
Pragma: no-cache
Server: Apache-Coyote/1.1
Transfer-Encoding: chunked
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block

{
    "authors": [
        {
            "birthplace": "Napoli", 
            "born": "1975-11-02", 
            "id": 1, 
            "name": "Roberto", 
            "surname": "Saviano"
        }
    ], 
    "currency": "EUR", 
    "description": "Gomorra desc", 
    "id": 1, 
    "isbn": "1234567890", 
    "price": 12.0, 
    "releaseDate": "2008-11-02", 
    "title": "Gomorra"
}

```


