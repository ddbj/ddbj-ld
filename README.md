# ddbj-ld

## requirement

```
python --version
Python 3.6.7
node -v
v12.11.1
```

## quick start 
### 1. create network

```
# 本番、ローカル環境の場合
docker network create ddbj_ld
docker network create ddbj_ld_stage
```

### 2. env setup
appliation.ymlは環境により変更すること

```
prod: 本番
stage: ステージング
dev: ローカル開発
```

```
cp -p .env.sample .env
vim .env
cp -p batch/src/main/resources/application.XXX.yml batch/src/main/resources/application.yml
vim batch/src/main/resources/batchlication.yml
cd ../batch
./gradlew bootJar
cd ../
./tools/initialize.sh
```

### 3. docker-compose up
引数は環境により適宜変更すること

```
prod: 本番
stage: ステージング
dev: ローカル開発用
```


```
./tools/run.sh XXX
Creating ddbjld_elasticsearch ... done
```

### 4. access

- access to http://localhost:3000/
```
curl -fsSL "localhost:9200/_cat/health?h=status"
green
docker-compose run --rm batch
```

## Appendix
1. Running Docker is by root user or user has sudo or user belong to docker group.
https://docs.docker.com/install/linux/docker-ce/centos/#install-using-the-convenience-script

2. Elasticsearch in Docker need least ddbj-ld memory size.
https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#_set_vm_max_map_count_to_at_least_262144
