#!/bin/bash

./wait-for-it.sh database:3306

java -Dlogging.file=logs/bookshop.log -DJDBC_DATABASE_HOSTNAME=database -DJDBC_DATABASE_PORT=3306 -jar bookshop.jar 
