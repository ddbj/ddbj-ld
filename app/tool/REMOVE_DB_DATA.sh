#!/usr/bin/env sh

# 作成されたテーブルデータを削除するコマンド
# ローカル環境にpsqlがインストールされていることが使用条件
psql -U root -h localhost ddbj << EOF
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

curl -X DELETE -fsSL "localhost:9200/bioproject"
curl -X DELETE -fsSL "localhost:9200/biosample"
curl -X DELETE -fsSL "localhost:9200/study" 
curl -X DELETE -fsSL "localhost:9200/sample" 
curl -X DELETE -fsSL "localhost:9200/submission"
curl -X DELETE -fsSL "localhost:9200/experiment"
curl -X DELETE -fsSL "localhost:9200/run"  
curl -X DELETE -fsSL "localhost:9200/analysis"
curl "localhost:9200/_search"
