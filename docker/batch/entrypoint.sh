#!/bin/bash

set -euo pipefail

echo "ACTION: $ACTION"
echo "DATE: $DATE"
echo "ENV:$ENV"

# docker compose up で一応呼ばれるが、batch 処理自体は行わないので、即時終了する
if [[ -z "$ACTION" ]]; then
  echo "Since ACTION is not set, the prcess exits."
  exit 0
fi

while :; do
  health="$(curl -fsSL "${ES_HOST}:${ES_PORT}/_cat/health?h=status")"
  if [ "$health" = "green" ]; then
    break
  fi
  sleep 1
done

if [ "$ENV" = "dev" ]; then
  ./gradlew build --quiet
  ./gradlew bootRun --args="$ACTION $DATE"
else
  java -jar -Dspring.profiles.active=docker build/libs/ddbj-ld-batch-0.0.1-SNAPSHOT.jar "$ACTION" "$DATE"
fi
