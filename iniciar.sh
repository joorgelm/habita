#!/bin/bash

#export POSTGRES_DB_NAME=habita
#export POSTGRES_USER=pgadmin
#export POSTGRES_PASSWORD=pgadmin
#export DATABASE_PORT=5432
#export DATABASE_HOST=localhost

mvn clean package -Dmaven.test.skip=true

docker-compose up --build
