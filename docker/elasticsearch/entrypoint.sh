#!/bin/bash

set -euo pipefail

handle_signal() {
  sig=$1
  echo "Received signal $sig"
  kill "-$sig" "$es_pid"
}

for sig in TERM INT QUIT HUP ABRT; do
  trap 'handle_signal $sig' "$sig"
done

wait_for_elasticsearch() {
  set +eo pipefail
  while :; do
    health="$(curl -fsSL "localhost:9200/_cat/health?h=status")"
    if [[ "$health" == "green" ]] || [[ "$health" == "yellow" ]]; then
      break
    fi
    sleep 1
  done
  set -eo pipefail
}

put_mapping() {
  local index="$1"
  local file="$2"
  curl -H "Content-Type:application/json" -XPUT "localhost:9200/_index_template/$index" -d "@$file" >/dev/null 2>&1
}

echo "Starting Elasticsearch..."
bash /usr/local/bin/docker-entrypoint.sh eswrapper &

es_pid=$!

echo "Waiting for Elasticsearch to start..."
wait_for_elasticsearch

echo "Elasticsearch started."

echo "Start to input initial data."

# JGAのマッピング
put_mapping "jga_study_mapping" "/usr/share/elasticsearch/config/templates/jga-study-mapping.json"
put_mapping "jga_dataset_mapping" "/usr/share/elasticsearch/config/templates/jga-dataset-mapping.json"
put_mapping "jga_policy_mapping" "/usr/share/elasticsearch/config/templates/jga-policy-mapping.json"
put_mapping "jga_dac_mapping" "/usr/share/elasticsearch/config/templates/jga-dac-mapping.json"

# BIOPROJECTのマッピング
put_mapping "bioproject_mapping" "/usr/share/elasticsearch/config/templates/bioproject-mapping.json"

# BIOSAMPLEのマッピング
put_mapping "biosample_mapping" "/usr/share/elasticsearch/config/templates/biosample-mapping.json"

# DRAのマッピング
put_mapping "sra_submission_mapping" "/usr/share/elasticsearch/config/templates/sra-submission-mapping.json"
put_mapping "sra_experiment_mapping" "/usr/share/elasticsearch/config/templates/sra-experiment-mapping.json"
put_mapping "sra_analysis_mapping" "/usr/share/elasticsearch/config/templates/sra-analysis-mapping.json"
put_mapping "sra_run_mapping" "/usr/share/elasticsearch/config/templates/sra-run-mapping.json"
put_mapping "sra_study_mapping" "/usr/share/elasticsearch/config/templates/sra-study-mapping.json"
put_mapping "sra_sample_mapping" "/usr/share/elasticsearch/config/templates/sra-sample-mapping.json"

echo "Finish to input initial data."

# Elasticsearchのプロセスが終了するのを待つ
wait "$es_pid"
