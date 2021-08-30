#!/bin/bash

# DRAのデータを削除するコマンド
# 環境にpsqlがインストールされていることが使用条件

source .env > /dev/null 2>&1
export PGPASSWORD=${PUBLIC_DB_PASSWORD} > /dev/null 2>&1

Port=9200
PostgrePort=5432

if [ "$Env" = "stage" ]; then
 Port=9202
 PostgrePort=5433
fi

psql -U root -h localhost -p ${PostgrePort}  -d public_db > /dev/null 2>&1 << EOF
TRUNCATE TABLE t_dra_analysis;
TRUNCATE TABLE t_dra_experiment;
TRUNCATE TABLE t_dra_run;
TRUNCATE TABLE t_dra_sample;
TRUNCATE TABLE t_dra_study;
TRUNCATE TABLE t_dra_submission;
EOF

curl -X DELETE -fsSL "localhost:${Port}/dra-study" > /dev/null 2>&1
curl -X DELETE -fsSL "localhost:${Port}/dra-sample" > /dev/null 2>&1
curl -X DELETE -fsSL "localhost:${Port}/dra-submission" > /dev/null 2>&1
curl -X DELETE -fsSL "localhost:${Port}/dra-experiment" > /dev/null 2>&1
curl -X DELETE -fsSL "localhost:${Port}/dra-run" > /dev/null 2>&1
curl -X DELETE -fsSL "localhost:${Port}/dra-analysis" > /dev/null 2>&1


