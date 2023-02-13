#!/bin/sh

cp -p docker-compose-test.yml docker-compose.yml
cp -p front/src/config.stage.js front/src/config.js
cp -p .env.test .env

mkdir logs
chmod +t logs

# ダミー用ディレクトリの作成
mkdir ext_data
chmod +t ext_data

# batchコンテナのデータダウンロード・データ展開用ディレクトリの作成
mkdir -p data/batch_out data/public
chmod -R +t data
chmod 777 data/elasticsearch data/elasticsearch2

# elasticsearch, postgresql等のデータ永続化用ディレクトリの作成
mkdir -p persistence_data/public_db persistence_data/repos_db persistence_data/elasticsearch persistence_data/elasticsearch2 persistence_data/file persistence_data/clamav
chmod -R +t persistence_data

chmod 600 .env

# ベンチマークテスト用のjava設定ファイルの作成
cp -p batch/src/main/resources/application.properties-test  batch/src/main/resources/application.properties
cp -p batch/src/main/resources/ddbj-batch.properties-test  batch/src/main/resources/ddbj-batch.properties
cp -p api/src/main/resources/application.properties-test api/src/main/resources/application.properties
cp -p api/src/main/resources/ddbj-api.properties-test api/src/main/resources/ddbj-api.properties
