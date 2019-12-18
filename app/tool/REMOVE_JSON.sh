#!/usr/bin/env sh
# 作成されたテストデータのJSONを削除するコマンド
SCRIPT_CALLED_DIR=`pwd`
SCRIPT_LOCATED_DIR=${SCRIPT_CALLED_DIR}/`dirname $0`
cd ${SCRIPT_LOCATED_DIR}
cd ../

echo '削除対象：'
ls ./src/main/resources/json/bioproject/bioproject.json
ls ./src/main/resources/json/biosample/biosample_set.json
ls -1 ./src/main/resources/json/TEST*

rm -f ./src/main/resources/json/bioproject/bioproject.json
rm -f ./src/main/resources/json/biosample/biosample_set.json

rm -fr ./src/main/resources/json/TEST*
