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

To test the api try with a curl
```sh
$ curl --user admin:password01 http://172.17.0.3:8080/bookshop/api/books
```

or if you prefer httpie ( https://github.com/jkbrzt/httpie )
```sh
$ http -v -a luca:password01 GET http://172.17.0.3:8080/bookshop/api/books
```
