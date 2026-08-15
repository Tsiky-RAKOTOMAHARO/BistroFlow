#!/bin/bash

mvn clean install

#docker
cd docker

docker compose down -v
echo "Docker reinitialiser"

docker compose up -d
