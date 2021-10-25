#!/bin/bash

# JGAのデータを削除するコマンド
# 環境にpsqlがインストールされていることが使用条件

source .env > /dev/null 2>&1
export PGPASSWORD=${PUBLIC_DB_PASSWORD} > /dev/null 2>&1

DBPort=5432
ESPort=9200

if [ ${ENV} = "Staging" ]; then
 DBPort=5433
 ESPort=9202
fi

psql -U root -h localhost -p ${DBPort}  -d ${PUBLIC_DB} > /dev/null 2>&1 << EOF
  TRUNCATE TABLE t_jga_analysis_study;
  TRUNCATE TABLE t_jga_data_experiment;
  TRUNCATE TABLE t_jga_dataset_analysis;
  TRUNCATE TABLE t_jga_dataset_data;
  TRUNCATE TABLE t_jga_dataset_policy;
  TRUNCATE TABLE t_jga_date;
  TRUNCATE TABLE t_jga_experiment_study;
EOF

curl -X DELETE -fsSL "localhost:${ESPort}/jga-study" > /dev/null 2>&1
curl -X DELETE -fsSL "localhost:${ESPort}/jga-dataset" > /dev/null 2>&1
curl -X DELETE -fsSL "localhost:${ESPort}/jga-policy" > /dev/null 2>&1
curl -X DELETE -fsSL "localhost:${ESPort}/jga-dac" > /dev/null 2>&1

