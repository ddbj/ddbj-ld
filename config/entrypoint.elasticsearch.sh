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
#curl -H "Content-Type: application/json" -XPUT "localhost:9200/meo_name?pretty" --data-binary "@/usr/share/elasticsearch/meo/meo_name_mapping.json"
#curl -H "Content-Type: application/x-ndjson" -XPOST "localhost:9200/_bulk?pretty" --data-binary "@/usr/share/elasticsearch/meo/meo_name_list.ndjson" > /dev/null
echo "Finish to input initial data."
tail -f /dev/null
