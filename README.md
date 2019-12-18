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
docker network create ddbj_ld
```

### 2. env setup

```
cp -p .env.sample .env
vim .env
cp -p app/src/main/resources/application.sample.yml app/src/main/resources/application.yml
vim app/src/main/resources/application.yml
cd front
npm install
npm run build
cd ../app
./gradlew bootJar
cd ../
```

### 3. docker-compose up

```
docker-compose up -d
Creating ddbjld_elasticsearch ... done
```

### 4. access

- access to http://localhost:3000/
```
curl -fsSL "localhost:9200/_cat/health?h=status"
green
docker-compose run --rm app
```
