#!/bin/bash

echo "TARGET_DB: $TARGET_DB"
./gradlew bootJar

while :
do
  health="$(curl -fsSL "${ES_HOST}:${ES_PORT}/_cat/health?h=status")"
  if [ "$health" = "green" ]; then
    break
  fi
  sleep 1
done

java -jar -Dspring.profiles.active=docker build/libs/ddbj-ld-batch-0.0.1-SNAPSHOT.jar "$TARGET_DB"


