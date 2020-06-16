#!/bin/bash

while :
do
  health="$(curl -fsSL "${ES_HOST}:${ES_PORT}/_cat/health?h=status")"
  if [ "$health" = "green" ]; then
    break
  fi
  sleep 1
done

java -jar -Dspring.profiles.active=docker build/libs/${JAR_TARGET}

