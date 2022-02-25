# ddbj-ld

## requirement

```bash
python --version
Python 3.6.7
node -v
v12.11.1
```

## quick start

### 2. env setup

```bash
# 初期セットアップ、必要なディレクトリを作成しパーミッションを付与
./bin/setup.sh [dev or stage or prod]

# 環境変数の設定
vim .env

# .envの設定例
# Development or Staging or Production
ENV=Production
# Dockerを実行したいユーザーのID
UID=0
# Dockerを実行したいグループのID
GID=0
# postgreSQLのコンテナpublic_dbの設定
PUBLIC_DB_USER=admin
PUBLIC_DB_PASSWORD=***
PUBLIC_DB_INITDB_ARGS=--encoding=UTF-8
PUBLIC_DB=public_db
PUBLIC_DB_HOSTNAME=public_db
# postgreSQLのコンテナrepos_dbの設定
REPOS_DB_USER=admin
REPOS_DB_PASSWORD=***
REPOS_DB_INITDB_ARGS=--encoding=UTF-8
REPOS_DB=repos_db
REPOS_DB_HOSTNAME=repos_db
# バッチが実行するアクション(JGA登録処理など)
ACTION=registerJGA
# バッチが取り込むデータ格納する場所のルート
DATA_DIR=/home/w3ddbjld
# SRAの登録バッチが登録開始する基準日（YYYYMMDD）
DATE=20211014
# Elasticsearch, Postgresなどのデータを永続化するためのディレクトリ
PERSISTENCE_DIR=/home/hoge/data
# ログディレクトリ
LOG_DIR=/home/hoge/logs

# バッチの設定
vim batch/src/main/resources/application.properties
vim batch/src/main/resources/ddbj-batch.properties

# APIの設定
vim api/src/main/resources/application.properties
vim api/src/main/resources/ddbj-api.properties

# VMに割くメモリを増やす
# この設定がないとElasticsearchが起動しない
# MacでもWSL2を利用したWindowsでも同じ
sudo vim /etc/sysctl.d/99-sysctl.conf
# 下記を追加
vm.max_map_count = 262144
sudo sysctl --system

# APIをビルド
docker-compose run --rm [コンテナ名は環境によって変更 api or api_stage] ./gradlew bootJar

# バッチをビルド
docker-compose run --rm [コンテナ名は環境によって変更 batch or batch_stage] ./gradlew bootJar
```

### 3. up

```bash
docker-compose up -d
```

### 4. run

- バッチは下記のように実行する

```bash
docker-compose run --rm -e ACTION=[ACTION] -e DATE=[DATE(yyyymmdd形式)] [コンテナ名は環境によって変更 batch or batch_stage]
```

- アクションは下記のとおり、右側の物理名をACTIONに指定すること

```text
# 取得
NCBI出力分BioProject取得：getBioProject
NCBI出力分BioSample取得：getBioSample
SRA取得（XML + SRA_Accessions.tabを取得）：getSRA
SRA取得（更新差分のXML + SRA_Accessions.tabを取得）：getSRAUpdated

# 関係登録
NCBI出力分SRA関係登録：registerSRAAccessions
DDBJ出力分DRA関係登録：registerDRAAccessions

# 登録
JGA登録：registerJGA
NCBI出力分BioProject登録：registerBioProject
DDBJ出力分BioProject登録：registerDDBJBioProject
NCBI出力分BioSample登録：registerBioSample
DDBJ出力分BioSample登録：registerDDBJBioSample
NCBI出力分SRA登録：registerSRA
DDBJ出力分DRA登録：registerDRA

# 関係更新
NCBI出力分SRA関係更新：updateSRAAccessions
DDBJ出力分DRA関係更新：updateDRAAccessions

# 更新
NCBI出力分BioProject更新：updateBioProject
DDBJ出力分BioProject更新：updateDDBJBioProject
NCBI出力分BioSample更新：updateBioSample
DDBJ出力分BioSample更新：updateDDBJBioSample
NCBI出力分SRA更新：updateSRA
DDBJ出力分DRA更新：updateDRA

# バリデーション
JGAバリデーション：validateJGA
NCBI出力分BioProjectバリデーション：validateBioProject
NCBI出力分BioSampleバリデーション：validateDDBJBioProject
NCBI出力分BioSampleバリデーション：validateBioSample
DDBJ出力分BioSampleバリデーション：validateDDBJBioSample
NCBI出力分SRAバリデーション：validateSRA
DDBJ出力分DRAバリデーション：validateDRA
```

## 5. Generate API from Swagger

./api/doc/配下にあるSwaggerの定義を編集し、コードを生成。  
./api/build/generated/openapiディレクトリ配下にコードが生成される。

**※ 生成されたコードは可読性が低いのでSwaggerのアノテーションだけをコピーして使用している**

```bash
# 共通API
docker-compose run --rm api ./gradlew buildCommonAPI
# JVar登録系API
docker-compose run --rm api ./gradlew buildJVarAPI
# BioProject登録系API
docker-compose run --rm api ./gradlew buildBioProjectAPI
# BioSample登録系API
docker-compose run --rm api ./gradlew buildBioSampleAPI
```

## 6. Appendix

### 6-1. Running Docker is by root user or user has sudo or user belong to docker group
<https://docs.docker.com/install/linux/docker-ce/centos/#install-using-the-convenience-script>

### 6-2. Elasticsearch in Docker need least ddbj-ld memory size
<https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#_set_vm_max_map_count_to_at_least_262144>

## 7. Memo

- localで動かすときはdockerのメモリを4GB以上割り当てる
