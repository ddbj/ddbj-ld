#!/bin/bash

# 作成されたテーブルデータを削除するコマンド
# 環境にpsqlがインストールされていることが使用条件
# DRAのデータを削除してElasticsearchのMappingを再定義する
# $1:環境(dev, stage, prod)

Env=$1
Port=9200
PostgrePort=5432

if [ "$Env" = "stage" ]; then
 Port=9202
 PostgrePort=5433
fi

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

EOF

curl -X DELETE -fsSL "localhost:${Port}/bioproject?pretty"
curl -X DELETE -fsSL "localhost:${Port}/biosample?pretty"
curl -X DELETE -fsSL "localhost:${Port}/dra-study?pretty"
curl -X DELETE -fsSL "localhost:${Port}/dra-sample?pretty"
curl -X DELETE -fsSL "localhost:${Port}/dra-submission?pretty"
curl -X DELETE -fsSL "localhost:${Port}/dra-experiment?pretty"
curl -X DELETE -fsSL "localhost:${Port}/dra-run?pretty"
curl -X DELETE -fsSL "localhost:${Port}/dra-analysis?pretty"

# TODO Mapping定義

