#!/bin/sh

# env setup
DOCKER_BUILD_DIR=target-docker
DOCKER_SRC_DIR=docker
JAVA_TARGET_DIR=target
MIDDLEWARE_IMAGE_NAME="abs/bookshop-middleware"


echo "Building book-service application"
mvn clean package -DskipTest=true

if [-d "$DOCKER_BUILD_DIR" ]; then
 rm -rf $DOCKER_BUILD_DIR
fi

mkdir $DOCKER_BUILD_DIR

echo "START docker book-service image build"

mkdir -p "$DOCKER_BUILD_DIR/middleware/files"
cp "$DOCKER_SRC_DIR/middleware/Dockerfile" "$DOCKER_BUILD_DIR/middleware"
cp "$JAVA_TARGET_DIR/bookshop-0.0.1-SNAPSHOT.jar"  "$DOCKER_BUILD_DIR/middleware/files"


docker build -t $MIDDLEWARE_IMAGE_NAME "$DOCKER_BUILD_DIR/middleware"







