#!/bin/bash

# リソース統合の環境をスタートする(バッチ以外)コマンド

Env=$1
ESPort=9200
PUBLIC_DB=public_db
REPOS_DB=repos_db
FRONT=front
API=api
ELASTIC_SEARCH_1=elasticsearch
ELASTIC_SEARCH_2=elasticsearch2

if [ "$Env" = "stage" ]; then
  ESPort=9202
  PUBLIC_DB=public_db_stage
  REPOS_DB=repos_db_stage
  FRONT=front_stage
  API=api_stage
  ELASTIC_SEARCH_1=elasticsearch_stage
  ELASTIC_SEARCH_2=elasticsearch2_stage
fi

docker-compose up -d $PUBLIC_DB $REPOS_DB $FRONT $API $ELASTIC_SEARCH_1 $ELASTIC_SEARCH_2

while :
do
  health="$(curl fsSL "localhost:${ESPort}/_cat/health?h=status")"
  if [ "$health" = "green" ]; then
    break
  fi
  sleep 1
done

echo "起動完了"
echo ""