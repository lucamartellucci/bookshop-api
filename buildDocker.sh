#!/bin/sh

echo "Building application"
#mvn clean package -DskipTest=true

mkdir -p docker/middleware/files
cp target/bookshop-0.0.1-SNAPSHOT.jar docker/middleware/files

echo "Building a set of docker images in order to run the Bookshop Application"

docker build -t abs/bookshop-middleware docker/middleware/

rm -rf docker/middleware/files






