#!/bin/bash
echo "Starting Spring Application..."

echo "ENV:$ENV"

if [ "$ENV" = 'Development' ]; then
  ./gradlew build --continuous --quiet &
  ./gradlew bootRun
else
  java -Djava.security.egd=file:/dev/./urandom -jar ./build/libs/ddbj-ld-api-0.0.1-SNAPSHOT.jar
fi
