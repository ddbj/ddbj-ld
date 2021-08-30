#!/bin/bash

# BioSampleのデータを削除するコマンド
# 環境にpsqlがインストールされていることが使用条件

source .env > /dev/null 2>&1

ESPort=9200

if [ ${ENV} = "Staging" ]; then
 ESPort=9202
fi

curl -X DELETE -fsSL "localhost:${ESPort}/jga-study" > /dev/null 2>&1
curl -X DELETE -fsSL "localhost:${ESPort}/jga-dataset" > /dev/null 2>&1
curl -X DELETE -fsSL "localhost:${ESPort}/jga-policy" > /dev/null 2>&1
curl -X DELETE -fsSL "localhost:${ESPort}/jga-dac" > /dev/null 2>&1

