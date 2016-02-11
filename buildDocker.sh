#!/bin/sh

. java8.sh

echo "Building application"
mvn clean package -DskipTest=true

cp target/bookshop-0.0.1-SNAPSHOT.jar docker/middleware

echo "Building a set of docker images in order to run the Bookshop Application"

docker build -t bookshop-middleware docker/middleware/






