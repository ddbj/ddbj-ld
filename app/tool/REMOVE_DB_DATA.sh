#!/usr/bin/env sh

# 作成されたテーブルデータを削除するコマンド
# ローカル環境にpsqlがインストールされていることが使用条件
docker-compose up -d postgresql
docker-compose up -d elasticsearch

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
EOF

curl -X DELETE -fsSL "localhost:9200/bioproject"
curl -X DELETE -fsSL "localhost:9200/biosample"
curl -X DELETE -fsSL "localhost:9200/study" 
curl -X DELETE -fsSL "localhost:9200/sample" 
curl -X DELETE -fsSL "localhost:9200/submission"
curl -X DELETE -fsSL "localhost:9200/experiment"
curl -X DELETE -fsSL "localhost:9200/run"  
curl -X DELETE -fsSL "localhost:9200/analysis"
curl "localhost:9200/_search"

docker-compose down
docker-compose up -d postgresql
docker-compose down
