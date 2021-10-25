#!/bin/bash

echo "ACTION: $ACTION"
echo "DATE: $DATE"
./gradlew bootJar

while :
do
  health="$(curl -fsSL "${ES_HOST}:${ES_PORT}/_cat/health?h=status")"
  if [ "$health" = "green" ]; then
    break
  fi
  sleep 1
done

java -jar -Dspring.profiles.active=docker build/libs/ddbj-ld-batch-0.0.1-SNAPSHOT.jar "$ACTION" "$DATE"
