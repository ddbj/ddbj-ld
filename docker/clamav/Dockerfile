FROM mkodockx/docker-clamav:1.0.8-buster

# sudoがインストールされておらず、rootでないとbootstrap.shを編集できないためrootにスイッチ
USER 0:0

# ウイルス定義自動更新するデーモン起動のコマンドをコメントアウト
# 定義更新はRundeckなどジョブスケジューラで管理したほうが良いといった判断
RUN sed -i -e 's/freshclam -d/#freshclam -d/g' /bootstrap.sh

# 実行ユーザーをclamavに戻す
USER 101:102
