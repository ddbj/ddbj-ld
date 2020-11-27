#!/bin/bash
echo "Starting Spring Application..."

cd /api

if [ "$ENV" = "Development" ]; then
  ./gradlew bootRun
else
  ./gradlew bootJar
  java -Djava.security.egd=file:/dev/./urandom -jar ./build/libs/ddbj-ld-api-0.0.1-SNAPSHOT.jar
fi

