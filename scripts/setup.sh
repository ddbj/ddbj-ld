#!/bin/sh

set -euo pipefail

ENV=$1

if [[ -z "$ENV" ]] || [[ "$ENV" != "dev" && "$ENV" != "prod" ]]; then
  echo "Please specify the environment as 'bash setup.sh [ dev | prod ]'"
  exit 1
fi

echo "Setup for $ENV environment..."

set -x

if [ "$ENV" = "dev" ]; then
  cp -p docker-compose-dev.yml docker-compose.yml
  cp -p front/.env.dev front/.env
elif [ "$ENV" = "prod" ]; then
  cp -p docker-compose-prod.yml docker-compose.yml
  cp -p front/.env.prod front/.env
fi

mkdir -p logs
chmod +t logs
mkdir -p data/public_db data/repos_db data/elasticsearch
chmod -R +t data
chmod 777 data/elasticsearch
cp -p .env.sample .env
chmod 600 .env

cp -p batch/src/main/resources/application.properties-sample batch/src/main/resources/application.properties
cp -p batch/src/main/resources/ddbj-batch.properties-sample batch/src/main/resources/ddbj-batch.properties
cp -p api/src/main/resources/application.properties-sample api/src/main/resources/application.properties
cp -p api/src/main/resources/ddbj-api.properties-sample api/src/main/resources/ddbj-api.properties

set +x

echo "Setup completed..."

echo "Please edit the following files:"

echo "  - .env"
echo "  - batch/src/main/resources/application.properties"
echo "  - batch/src/main/resources/ddbj-batch.properties"
echo "  - api/src/main/resources/application.properties"
echo "  - api/src/main/resources/ddbj-api.properties"
