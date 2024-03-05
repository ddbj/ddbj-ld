#!/bin/bash

set -u

DATE=$1

if [[ -z "$DATE" ]]; then
    DATE=20240120
fi

actions=(
    "getBioProject"       # NCBI 出力分 BioProject 取得
    "getBioSample"        # NCBI 出力分 BioSample 取得
    "getSRAUpdated"       # SRA 取得（更新差分の XML + SRA_Accessions.tab を取得）
    "updateSRAAccessions" # NCBI 出力分 SRA 関係更新
    "updateBioProject"    # NCBI 出力分 BioProject 更新
    "updateBioSample"     # NCBI 出力分 BioSample 更新
    "updateSRA"           # NCBI 出力分 SRA 更新
)

failed_actions=()

log_dir=./update-db-ncbi-logs
mkdir -p $log_dir

for action in "${actions[@]}"; do
    start_time=$(date +%s)
    echo "Start to run $action at $(date)"
    docker compose exec -T -e ACTION=$action -e DATE=$DATE batch bash /usr/local/bin/entrypoint.sh 2>&1 | tee $log_dir/$action.log
    if [ $? -ne 0 ]; then
        failed_actions+=($action)
    fi
    end_time=$(date +%s)
    duration=$((end_time - start_time))
    echo "Finish to run $action at $(date)"
    echo "The duration of $action is $duration seconds"
done

if [[ ${#failed_actions[@]} -ne 0 ]]; then
    echo "=== Failed to below actions: ==="
    for action in "${failed_actions[@]}"; do
        echo "  - $action"
    done
    exit 1
else
    echo "=== All actions succeeded ==="
    exit 0
fi
