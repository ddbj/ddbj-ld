#!/bin/bash

set -euo pipefail

echo "Starting Spring Application..."

echo "ENV:$ENV"

if [ "$ENV" = "dev" ]; then
  ./gradlew build
  ./gradlew bootRun
else
  java -Djava.security.egd=file:/dev/./urandom -jar ./build/libs/ddbj-ld-api-0.0.1-SNAPSHOT.jar
fi
