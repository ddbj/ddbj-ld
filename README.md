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
./tools/initialize.sh [dev or stage or prod]

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
# バッチの対象となるDB
# jga or dra or bioproject or biosample or all
TARGET_DB=jga

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
```

### 3. run

```bash
docker-compose up -d
```

### 4. Generate API from Swagger

./api/doc/配下にあるSwaggerの定義を編集し、コードを生成。  
./api/buildディレクトリ配下にコードが生成される。

```bash
cd ./api
./gradlew generateSwaggerCode
```

## Appendix

### 1. Running Docker is by root user or user has sudo or user belong to docker group
<https://docs.docker.com/install/linux/docker-ce/centos/#install-using-the-convenience-script>

### 2. Elasticsearch in Docker need least ddbj-ld memory size
<https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#_set_vm_max_map_count_to_at_least_262144>

## Memo

- localで動かすときはdockerのメモリを4GB以上割り当てる

