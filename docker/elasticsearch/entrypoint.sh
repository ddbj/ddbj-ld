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
curl -H "Content-Type: application/json" -XPUT localhost:9200/_template/default -d @/usr/share/elasticsearch/config/templates/default.json > /dev/null

# JGAのマッピング
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/jga_study_mapping -d @/usr/share/elasticsearch/config/templates/jga-study-mapping.json > /dev/null
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/jga_dataset_mapping -d @/usr/share/elasticsearch/config/templates/jga-dataset-mapping.json > /dev/null
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/jga_policy_mapping -d @/usr/share/elasticsearch/config/templates/jga-policy-mapping.json > /dev/null
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/jga_dac_mapping -d @/usr/share/elasticsearch/config/templates/jga-dac-mapping.json > /dev/null
# TODO 必要に応じてAPIを使用しないと設定できない設定を追加していく

echo "Finish to input initial data."
tail -f /dev/null
