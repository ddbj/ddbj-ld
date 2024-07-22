#!/bin/bash

set -u

DATE=$1

if [[ -z "$DATE" ]]; then
    DATE=20240120
fi

actions=(
    "getSRA"                # SRA 取得（XML + SRA_Accessions.tab を取得）
    "registerSRAAccessions" # NCBI 出力分 SRA 関係登録
    "registerSRA"           # NCBI 出力分 SRA 登録
    "getBioProject"         # NCBI 出力分 BioProject 取得
    "registerBioProject"    # NCBI 出力分 BioProject 登録
    "getBioSample"          # NCBI 出力分 BioSample 取得
    "registerBioSample"     # NCBI 出力分 BioSample 登録
)

failed_actions=()

log_dir=./init-db-logs
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
