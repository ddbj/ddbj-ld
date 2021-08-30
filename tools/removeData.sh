#!/bin/bash

# 作成されたテーブルデータを削除するコマンド
# 環境にpsqlがインストールされていることが使用条件

./tools/removeJgaData.sh
./tools/removeBioProjectData.sh
./tools/removeBioSampleData.sh
./tools/removeDraData.sh
