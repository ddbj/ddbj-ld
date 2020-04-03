#!/bin/bash

# コンテナ状態確認用のコマンド
# 第一引数: prod or stage or dev

Target="docker-compose-${1}.yml"

if [ ! -f $Target ]; then
  echo "[ERROR]ファイルが存在しません"
  exit 1
fi

docker-compose --file $Target ps


