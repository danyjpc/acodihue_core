#!/bin/sh

docker stop acodihue-core

docker rm acodihue-core

docker rmi acodihue-core_acodihue

mvn clean package
docker-compose up

read