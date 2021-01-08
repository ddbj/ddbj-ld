# ddbj-ld

## requirement

```bash
python --version
Python 3.6.7
node -v
v12.11.1
```

## quick start

### 1. create network

```bash
# 本番、ローカル環境の場合
docker network create ddbj_ld
docker network create ddbj_ld_stage
```

### 2. env setup

application.yml,docker-compose.ymlは環境によりコピーするファイル変更すること。  

```bash
cp -p .env.sample .env
vim .env
cp -p batch/src/main/resources/application.XXX.yml batch/src/main/resources/application.yml
vim batch/src/main/resources/application.yml
cp -p docker-compose-XXX.yml docker-compose.yml
cd ./batch
./gradlew bootJar
cd ../front
cp -p .env.sample .env
cp -p [環境に合わせたsrc/config.*.js] src/config.js
cd ../
./tools/initialize.sh
```

```bash
# .envの設定例
ENV=Production
PUBLIC_DB_USER=admin
PUBLIC_DB_PASSWORD=***
PUBLIC_DB_INITDB_ARGS=--encoding=UTF-8
PUBLIC_DB=public_db
PUBLIC_DB_HOSTNAME=public_db
JVAR_DB_USER=admin
JVAR_DB_PASSWORD=***
JVAR_DB_INITDB_ARGS=--encoding=UTF-8
JVAR_DB=jvar_db
JVAR_DB_HOSTNAME=jvar_db
OPENDJ_PASS=***
TARGET_DB=jga
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

1. Running Docker is by root user or user has sudo or user belong to docker group.
https://docs.docker.com/install/linux/docker-ce/centos/#install-using-the-convenience-script

2. Elasticsearch in Docker need least ddbj-ld memory size.
https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#_set_vm_max_map_count_to_at_least_262144

## Memo

・localで動かすときはdockerのメモリを4GB以上割り当てる
