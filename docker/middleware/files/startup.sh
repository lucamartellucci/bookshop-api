#!/bin/bash

./wait-for-it.sh database:3306

java -Dlogging.file=logs/bookshop.log -DJDBC_DATABASE_HOSTNAME=database -jar bookshop.jar 