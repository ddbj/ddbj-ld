#!/bin/sh

ENV=$1

if [ "$ENV" = "dev" ]; then
  # ローカル開発環境の場合、ホットリロードのため必要だが他では不要
  cp -p front/.env.sample front/.env
  cp -p docker-compose-dev.yml docker-compose.yml
  cp -p front/src/config.dev.js front/src/config.js
  # デバッグ用の設定が入っているため、開発環境の場合のみコピー
  cp -p front/.env.sample front/.env
elif [ "$ENV" = "stage" ]; then
  cp -p docker-compose-stage.yml docker-compose.yml
  cp -p front/src/config.stage.js front/src/config.js
elif [ "$ENV" = "prod" ]; then
  cp -p docker-compose-prod.yml docker-compose.yml
  cp -p front/src/config.prod.js front/src/config.js
fi

# TODO ステージング・プロダクションのESのノード数が決まったら作成する永続化するデータディレクトリを環境に応じて追加作成する
# TODO 環境変数の値に応じて作るパスを変更する
mkdir logs
chmod +t logs
mkdir -p data/public_db data/repos_db data/elasticsearch data/elasticsearch2 data/elasticsearch3 data/elasticsearch4 data/file

chmod -R +t data
cp -p .env.sample .env
cp -p batch/src/main/resources/application.properties-sample  batch/src/main/resources/application.properties
cp -p batch/src/main/resources/ddbj-batch.properties-sample  batch/src/main/resources/ddbj-batch.properties
cp -p api/src/main/resources/application.properties-sample api/src/main/resources/application.properties
cp -p api/src/main/resources/ddbj-api.properties-sample api/src/main/resources/ddbj-api.properties