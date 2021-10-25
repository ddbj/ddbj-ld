#!/bin/bash

# BioProjectのデータを削除するコマンド
# 環境にpsqlがインストールされていることが使用条件

source .env > /dev/null 2>&1
export PGPASSWORD=${PUBLIC_DB_PASSWORD}

DBPort=5432
ESPort=9200

if [ ${ENV} = "Staging" ]; then
 DBPort=5433
 ESPort=9202
fi

psql -U root -h localhost -p ${DBPort}  -d ${PUBLIC_DB} > /dev/null 2>&1 << EOF
  TRUNCATE TABLE t_bioproject_biosample;
EOF

curl -X DELETE -fsSL "localhost:${ESPort}/bioproject" > /dev/null 2>&1

