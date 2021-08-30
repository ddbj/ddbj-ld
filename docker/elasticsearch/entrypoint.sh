#!/bin/bash
echo "Starting Elasticsearch..."
bash /usr/local/bin/docker-entrypoint.sh &
echo "Elasticsearch started."

while :
do
  health="$(curl -fsSL "localhost:9200/_cat/health?h=status")"
  if [[ "$health" == "green" ]] || [[ "$health" == "yellow" ]]; then
    break
  fi
  sleep 1
done

echo "Start to input initial data."
# shardとreplica数を変更
curl -H "Content-Type: application/json" -XPUT localhost:9200/_template/default -d '{ "template": ["*"], "index_patterns": ["*"], "order": -1, "settings": {"number_of_shards": "72",
"number_of_replicas": "2"}}' > /dev/null

# TODO 必要に応じてAPIを使用しないと設定できない設定を追加していく

echo "Finish to input initial data."
tail -f /dev/null
