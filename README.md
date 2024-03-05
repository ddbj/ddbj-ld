# ddbj-ld

"INSDC BioProject/BioSample/SRA, JGA データをアクセッション番号やキーワードで検索" するための Web Application [DDBJ-Search](https://ddbj.nig.ac.jp/search).

## Requirements

- `docker`, `docker-compose` を利用して、deploy を行う
- 基本的に、遺伝研スパコンの node 上での実行・開発を想定している
  - `/usr/local/resources/bioproject` といった、スパコン内の resource への file access が必要
  - BioProject/BioSample DB（PostgreSQL）の情報も取得しているため、それら DB への access 許可が必要

## Deployment

- 起動する container として、
  - `elasticsearch`: Elasticsearch, api/front から data source として参照される
  - `public_db`: PostgreSQL, データ投入の batch 処理の中間 DB として使われる
  - `repos_db`: PostgreSQL, データ投入の batch 処理の中間 DB として使われる
  - `batch`: Java, 各 DB にデータを投入する
  - `api`: Java Spring Boot, 実際には各 entry の詳細ページの html を返す
  - `front`: Next.js SPA, 検索 Page (Home 画面) を表示する

### 0. Host 側の設定

Elasticsearch 用の Virtual memory を設定する。 (ref.: [elastic - docs - Virtual memory](https://www.elastic.co/guide/en/elasticsearch/reference/current/vm-max-map-count.html)) この設定を永続化する場合は、`/etc/sysctl.conf` を編集してください。

```bash
sysctl -w vm.max_map_count=262144
```

---

Docker container のコンテナサイズ制限を設定する。(ref.: [Docker - docs - dockerd daemon](https://docs.docker.jp/engine/reference/commandline/dockerd.html)) この設定を永続化する場合は、`/etc/docker/daemon.json` を編集してください。

```bash
dockerd --storage-opt dm.basesize=50G
```

### 1. 設定ファイルの作成と編集

[./scripts/setup.sh](./scripts/setup.sh) を用いて、設定ファイルや log dir を作成する。

```bash
$ bash ./scripts/setup.sh prod
...
Please edit the following files:
  - .env
  - batch/src/main/resources/application.properties
  - batch/src/main/resources/ddbj-batch.properties
  - api/src/main/resources/application.properties
  - api/src/main/resources/ddbj-api.properties
```

これらのファイルを編集する。(TODO: 実際の設定は環境に強く依存する。その内容をこの docs に記述するかは、要 discussion)

### 2. Docker container の起動

`docker compose` を用いて、container を起動する。

```bash
docker compose up -d --build
```

### 3. 初期データの投入

[./script/init_db.sh] を用いて、PostgreSQL と Elasticsearch に初期データを投入する。DATE を設定する場合、<https://ftp.ncbi.nlm.nih.gov/sra/reports/Metadata/> から `NCBI_SRA_Metadata_Full_yyyymmdd.tar.gz` の `yyyymmdd` を指定する。

```bash
bash ./scripts/init_db.sh 20240120
```

## Development

上の "Deployment" と同様の手順において、`bash ./scripts/setup.sh dev` を実行する。
