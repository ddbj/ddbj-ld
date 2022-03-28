#!/bin/bash

echo "ACTION: $ACTION"
echo "DATE: $DATE"
echo "ENV:$ENV"

while :
do
  health="$(curl -fsSL "${ES_HOST}:${ES_PORT}/_cat/health?h=status")"
  if [ "$health" = "green" ]; then
    break
  fi
  sleep 1
done

if [ "$ENV" = 'Development' ]; then
  ./gradlew build --continuous --quiet &
  ./gradlew bootRun --args="$ACTION $DATE"
else
  java -jar -Dspring.profiles.active=docker build/libs/ddbj-ld-batch-0.0.1-SNAPSHOT.jar "$ACTION" "$DATE"
fi
