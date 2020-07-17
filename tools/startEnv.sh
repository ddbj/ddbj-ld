#!/bin/bash

# リソース統合の環境をスタートする(バッチ以外)コマンド

Env=$1
ESPort=9200

if [ "$Env" = "stage" ]; then
 ESPort=9202
fi

docker-compose up -d postgresql elasticsearch elasticsearch2

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