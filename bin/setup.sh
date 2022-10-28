#!/bin/sh

ENV=$1

if [ "$ENV" = "dev" ]; then
  cp -p docker-compose-dev.yml docker-compose.yml
  cp -p front/.env.dev front/.env
elif [ "$ENV" = "stage" ]; then
  cp -p docker-compose-stage.yml docker-compose.yml
  cp -p front/.env.stage front/.env
elif [ "$ENV" = "prod" ]; then
  cp -p docker-compose-prod.yml docker-compose.yml
  cp -p front/.env.prod front/.env
fi

mkdir logs
chmod +t logs
mkdir -p data/public_db data/repos_db data/elasticsearch data/elasticsearch2 data/file data/clamav

chmod -R +t data
chmod 777 data/elasticsearch data/elasticsearch2
cp -p .env.sample .env
chmod 600 .env
cp -p batch/src/main/resources/application.properties-sample  batch/src/main/resources/application.properties
cp -p batch/src/main/resources/ddbj-batch.properties-sample  batch/src/main/resources/ddbj-batch.properties
cp -p api/src/main/resources/application.properties-sample api/src/main/resources/application.properties
cp -p api/src/main/resources/ddbj-api.properties-sample api/src/main/resources/ddbj-api.properties
