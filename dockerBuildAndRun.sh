#!/bin/bash

# env setup
DOCKER_BUILD_DIR=target-docker
DOCKER_SRC_DIR=docker
JAVA_TARGET_DIR=target
MIDDLEWARE_IMAGE_NAME="lucci/bookshop-middleware"

echo ""
echo "***************************"
echo "* 1 - Cleaning old builds *"
echo "***************************"

if [ -d "$DOCKER_BUILD_DIR" ]; then
 rm -rf $DOCKER_BUILD_DIR
fi

echo "Done"

echo ""
echo "*****************************************"
echo "* 2 - Building Book-Service application *"
echo "*****************************************"

mvn clean package -DskipTests


mkdir $DOCKER_BUILD_DIR
echo ""
echo "***************************************"
echo "* 3 - Build Book-Service docker image *"
echo "***************************************"

mkdir -p "$DOCKER_BUILD_DIR/middleware/files"
cp "$DOCKER_SRC_DIR/middleware/Dockerfile" "$DOCKER_BUILD_DIR/middleware"
cp "$JAVA_TARGET_DIR/bookshop.jar" "$DOCKER_BUILD_DIR/middleware/files"
cp "$DOCKER_SRC_DIR/middleware/files/startup.sh" "$DOCKER_BUILD_DIR/middleware/files"
cp "$DOCKER_SRC_DIR/middleware/files/wait-for-it.sh" "$DOCKER_BUILD_DIR/middleware/files"

docker build -t $MIDDLEWARE_IMAGE_NAME "$DOCKER_BUILD_DIR/middleware"


echo ""
echo "******************************"
echo "* 4 - Startup Docker Compose *"
echo "******************************"

cd docker
docker-compose up -d

BOOKSHOP_IP=$(docker inspect -f '{{ .NetworkSettings.IPAddress }}' docker_bookshop_1)
echo ""
echo "Bookshop endpoint available at http://$BOOKSHOP_IP:8080/bookshop/api"




