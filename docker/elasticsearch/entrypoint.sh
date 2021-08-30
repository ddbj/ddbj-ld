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

# JGAのマッピング
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/jga_study_mapping -d @/usr/share/elasticsearch/config/templates/jga-study-mapping.json > /dev/null 2>&1
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/jga_dataset_mapping -d @/usr/share/elasticsearch/config/templates/jga-dataset-mapping.json > /dev/null 2>&1
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/jga_policy_mapping -d @/usr/share/elasticsearch/config/templates/jga-policy-mapping.json > /dev/null 2>&1
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/jga_dac_mapping -d @/usr/share/elasticsearch/config/templates/jga-dac-mapping.json > /dev/null 2>&1

# BIOPROJECTのマッピング
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/bioproject_mapping -d @/usr/share/elasticsearch/config/templates/bioproject-mapping.json > /dev/null 2>&1

# BIOSAMPLEのマッピング
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/biosample_mapping -d @/usr/share/elasticsearch/config/templates/biosample-mapping.json > /dev/null 2>&1

# DRAのマッピング
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/dra_submission_mapping -d @/usr/share/elasticsearch/config/templates/dra-submission-mapping.json > /dev/null 2>&1
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/dra_experiment_mapping -d @/usr/share/elasticsearch/config/templates/dra-experiment-mapping.json > /dev/null 2>&1
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/dra_analysis_mapping -d @/usr/share/elasticsearch/config/templates/dra-analysis-mapping.json > /dev/null 2>&1
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/dra_run_mapping -d @/usr/share/elasticsearch/config/templates/dra-run-mapping.json > /dev/null 2>&1
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/dra_study_mapping -d @/usr/share/elasticsearch/config/templates/dra-study-mapping.json > /dev/null 2>&1
curl -H "Content-Type:application/json" -XPUT localhost:9200/_template/dra_sample_mapping -d @/usr/share/elasticsearch/config/templates/dra-sample-mapping.json > /dev/null 2>&1

echo "Finish to input initial data."
tail -f /dev/null
