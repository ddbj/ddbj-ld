#!/bin/bash

# 作成されたテーブルデータを削除するコマンド
# 環境にpsqlがインストールされていることが使用条件
# 第一引数: prod or stage or dev

Target="docker-compose-${1}.yml"

if [ ! -f $Target ]; then
  echo "[ERROR]ファイルが存在しません"
  exit 1
fi

docker-compose --file $Target up -d postgresql
docker-compose --file $Target up -d elasticsearch

sleep 15

psql -U root -h localhost ddbj << EOF
DROP TABLE bioproject_submission;
DROP TABLE submission_analysis;
DROP TABLE submission_experiment;
DROP TABLE biosample_experiment;
DROP TABLE experiment_run;
DROP TABLE run_biosample;
DROP TABLE study_submission;
DROP TABLE sample_experiment;
DROP TABLE bioproject_study;
DROP TABLE biosample_sample;

DROP TABLE bioproject;
DROP TABLE submission;
DROP TABLE analysis;
DROP TABLE experiment;
DROP TABLE biosample;
DROP TABLE run;
DROP TABLE study;
DROP TABLE sample;

DROP TABLE jga_relation;
EOF

curl -X DELETE -fsSL "localhost:9200/bioproject"
curl -X DELETE -fsSL "localhost:9200/biosample"
curl -X DELETE -fsSL "localhost:9200/dra-study"
curl -X DELETE -fsSL "localhost:9200/dra-sample"
curl -X DELETE -fsSL "localhost:9200/dra-submission"
curl -X DELETE -fsSL "localhost:9200/dra-experiment"
curl -X DELETE -fsSL "localhost:9200/dra-run"
curl -X DELETE -fsSL "localhost:9200/dra-analysis"

curl -X DELETE -fsSL "localhost:9200/jga-study"
curl -X DELETE -fsSL "localhost:9200/jga-dataset"
curl -X DELETE -fsSL "localhost:9200/jga-policy"
curl -X DELETE -fsSL "localhost:9200/jga-dac"
curl "localhost:9200/_search"

docker-compose  --file $Target down
docker-compose --file $Target up -d postgresql
docker-compose --file $Target down
