#!/bin/bash

cd backend || exit

# chargement .env

set -a 

source ../.env

set +a

echo "Demarrage app"

mvn spring-boot:run