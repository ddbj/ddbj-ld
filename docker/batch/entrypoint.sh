#!/bin/bash

set -euo pipefail

echo "ACTION: $ACTION"
echo "DATE: $DATE"
echo "ENV:$ENV"

# docker compose up で呼ばれ、container を起動し続ける。
if [[ "$ACTION" == "" || "$ACTION" == "init" ]]; then
  echo "Since ACTION is set to init or empty, the batch process will not be executed."

  if [[ "$ENV" == "prod" ]]; then
    echo "Since ENV is set to prod, build the jar file."
    ./gradlew build --quiet --stacktrace
  fi

  echo "Keep the container running."
  sleep infinity
fi

while :; do
  health="$(curl -fsSL "${ES_HOST}:${ES_PORT}/_cat/health?h=status")"
  if [ "$health" = "green" ]; then
    break
  fi
  sleep 1
done

if [[ "$ENV" == "dev" ]]; then
  ./gradlew bootRun --args="$ACTION $DATE"
else
  java -jar -Dspring.profiles.active=docker build/libs/ddbj-ld-batch-0.0.1-SNAPSHOT.jar "$ACTION" "$DATE"
fi
