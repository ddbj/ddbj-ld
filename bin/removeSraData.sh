#!/bin/bash

# DRAのデータを削除するコマンド
# 環境にpsqlがインストールされていることが使用条件

source .env >/dev/null 2>&1
export PGPASSWORD=${PUBLIC_DB_PASSWORD} >/dev/null 2>&1

Port=9200
PostgrePort=5432

psql -U root -h localhost -p ${PostgrePort} -d public_db >/dev/null 2>&1 <<EOF
TRUNCATE TABLE t_sra_analysis;
TRUNCATE TABLE t_sra_experiment;
TRUNCATE TABLE t_sra_run;
TRUNCATE TABLE t_sra_sample;
TRUNCATE TABLE t_sra_study;
TRUNCATE TABLE t_sra_submission;
EOF

curl -X DELETE -fsSL "localhost:${Port}/sra-study" >/dev/null 2>&1
curl -X DELETE -fsSL "localhost:${Port}/sra-sample" >/dev/null 2>&1
curl -X DELETE -fsSL "localhost:${Port}/sra-submission" >/dev/null 2>&1
curl -X DELETE -fsSL "localhost:${Port}/sra-experiment" >/dev/null 2>&1
curl -X DELETE -fsSL "localhost:${Port}/sra-run" >/dev/null 2>&1
curl -X DELETE -fsSL "localhost:${Port}/sra-analysis" >/dev/null 2>&1
