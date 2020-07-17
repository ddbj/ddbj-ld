#!/bin/bash

# 作成されたテーブルデータを削除するコマンド
# 環境にpsqlがインストールされていることが使用条件
# JGAのデータを削除してElasticsearchのMappingを再定義する
# $1:環境(dev, stage, prod)

Env=$1
DBPort=5432
ESPort=9200

if [ "$Env" = "stage" ]; then
 DBPort=5433
 ESPort=9202
fi

psql -U root -h localhost -p ${DBPort}  -d ddbj << EOF
  DELETE FROM jga_relation;
  DELETE FROM jga_date;
EOF

curl -X DELETE -fsSL "localhost:${ESPort}/jga-study?pretty"
curl -X DELETE -fsSL "localhost:${ESPort}/jga-dataset?pretty"
curl -X DELETE -fsSL "localhost:${ESPort}/jga-policy?pretty"
curl -X DELETE -fsSL "localhost:${ESPort}/jga-dac?pretty"

curl -XPUT "localhost:${ESPort}/jga-study?pretty" -d @tools/jga-study-mapping.json -H 'Content-Type: application/json'
curl -XPUT "localhost:${ESPort}/jga-dataset?pretty" -d @tools/jga-dataset-mapping.json -H 'Content-Type: application/json'
curl -XPUT "localhost:${ESPort}/jga-policy?pretty" -d @tools/jga-policy-mapping.json -H 'Content-Type: application/json'
curl -XPUT "localhost:${ESPort}/jga-dac?pretty" -d @tools/jga-dac-mapping.json -H 'Content-Type: application/json'

