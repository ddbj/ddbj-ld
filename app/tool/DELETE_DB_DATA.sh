#!/usr/bin/env sh

# 作成されたテーブルデータを削除するコマンド
# ローカル環境にpsqlがインストールされていることが使用条件
psql -U root -h localhost ddbj << EOF
DELETE FROM bioproject;
DELETE FROM submission;
DELETE FROM analysis;
DELETE FROM experiment;
DELETE FROM biosample;
DELETE FROM run;
DELETE FROM study;
DELETE FROM sample;
EOF
