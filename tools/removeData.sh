#!/bin/bash

# 作成されたテーブルデータを削除するコマンド
# 環境にpsqlがインストールされていることが使用条件

Port=9200
PostgrePort=5432

if [ $1 = "stage" ]; then
 Port=9202
 PostgrePort=5433
fi

docker-compose up -d postgresql elasticsearch elasticsearch2

psql -U root -h localhost -p ${PostgrePort}  -d ddbj << EOF
DELETE FROM bioproject_submission;
DELETE FROM submission_analysis;
DELETE FROM submission_experiment;
DELETE FROM biosample_experiment;
DELETE FROM experiment_run;
DELETE FROM run_biosample;
DELETE FROM study_submission;
DELETE FROM sample_experiment;
DELETE FROM bioproject_study;
DELETE FROM biosample_sample;

DELETE FROM bioproject;
DELETE FROM submission;
DELETE FROM analysis;
DELETE FROM experiment;
DELETE FROM biosample;
DELETE FROM run;
DELETE FROM study;
DELETE FROM sample;

DELETE FROM jga_relation;
DELETE FROM jga_date;
EOF

while :
do
  health="$(curl -fsSL "localhost:${Port}/_cat/health?h=status")"
  if [ "$health" = "green" ]; then
    break
  fi
  sleep 1
done

curl -X DELETE -fsSL "localhost:${Port}/bioproject"
curl -X DELETE -fsSL "localhost:${Port}/biosample"
curl -X DELETE -fsSL "localhost:${Port}/dra-study"
curl -X DELETE -fsSL "localhost:${Port}/dra-sample"
curl -X DELETE -fsSL "localhost:${Port}/dra-submission"
curl -X DELETE -fsSL "localhost:${Port}/dra-experiment"
curl -X DELETE -fsSL "localhost:${Port}/dra-run"
curl -X DELETE -fsSL "localhost:${Port}/dra-analysis"

curl -X DELETE -fsSL "localhost:${Port}/jga-study"
curl -X DELETE -fsSL "localhost:${Port}/jga-dataset"
curl -X DELETE -fsSL "localhost:${Port}/jga-policy"
curl -X DELETE -fsSL "localhost:${Port}/jga-dac"
curl "localhost:${Port}/_search"

curl -XPUT "localhost:${Port}/jga-study" -d @tools/jga-study-mapping.json -H 'Content-Type: application/json'

