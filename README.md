# ddbj-ld

## Requirement
Python3 or more

## quick start 
1. create network

```
docker network create ddbj_ld
```

2. docker-compose up

```
%docker-compose up -d
Creating ddbjld_elasticsearch ... done
```

3. access

```
%curl -fsSL "localhost:9200/_cat/health?h=status"
green
```
