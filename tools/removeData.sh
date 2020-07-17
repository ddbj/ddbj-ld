#!/bin/bash

# 作成されたテーブルデータを削除するコマンド
# 環境にpsqlがインストールされていることが使用条件
# $1:環境(dev, stage, prod)

Env=$1

./tools/removeDraData.sh "$Env"
./tools/removeJgaData.sh "$Env"
