#!/bin/bash

#docker
cd docker

docker compose down -v
echo "Docker reinitialiser"

docker compose up -d
echo "conteneur mis en place"