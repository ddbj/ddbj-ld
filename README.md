# ddbj-ld

## 目的
DDBJでデータベースを構築する際には、各データベース(BioProject, BioSample, SRA)のデータをtar.gzファイルからテキストファイルへ展開し、さらにそれらをPostgreSQLおよびElasticSearchに登録する処理を行っている。本ベンチマークの目的はこの一連の計算にかかる処理時間を計測することである。

## ベンチマーク環境の準備
- 本ベンチマークテストは（最小の構成では）物理計算機が１台とストレージシステム１式で動作する。
- DDBJのデータベース構築作業は遺伝研スパコンThin計算ノード Type 1aで行っているので、物理計算機のスペックについてはこれを参考にすること。https://sc.ddbj.nig.ac.jp/guides/hardware
- 遺伝研スパコンのOSはCentOS 7.5.1804である。本ベンチマークプログラムはUbuntu Linux 20.04でも動作する。
- ベンチマークテストプログラムはdocker-compose  (Docker version 20.10.17, Docker Compose version v2.6.0) を用いて、ElasticSearch, PostgreSQL、バッチプログラムその他のプログラムを物理計算機上にインストールする。

## ベンチマークプログラムのインストール
### 概要
インストール作業は下記４つの作業からなる。
1. ベンチマークプログラムをgithubから取得する。
2. ./bin/setup-test.sh スクリプトを実行し、必要なディレクトリやファイルを作成する。
3. ElasticSearchの実行に必要な設定を物理計算機に対して行う。
4. batchプログラムのビルドを行う。

### ベンチマークプログラムを取得する
```
$ git clone https://github.com/ddbj/ddbj-ld
$ cd ddbj-ld
$ git checkout benchmark2
```
### ./bin/setup-test.shスクリプトを実行し、必要なディレクトリやファイルを作成する
```
$ ./bin/setup-test.sh
```
これを実行すると、ddbj-ldディレクトリに以下のディレクトリが作られる。
- data/batch-out: SRA metadata archive fileをダウンロードするディレクトリ
- data/public: SRA metadata archive fileを展開するディレクトリ
- persistence_data/public_db: PostgreSQLデータベースを置くディレクトリ
- persistence_data/elasticsearch, persistence_data/elasticsearch2: ElasticSearchインデックスファイルを置くディレクトリ
- logs: ログファイルを置くディレクトリ

これらのディレクトリをddbj-ldディレクトリの下にまとめるのではなく別々の場所に作ることもできる。その場合は、設定ファイルの詳細についてのドキュメントを参照し、設定ファイルを手動で編集し調整する。

### ElasitcSearchの実行に必要な設定を物理計算機に対して行う
バーチャルメモリに割くメモリを増やす。この設定がないとElasticsearchが起動しない。MacでもWSL2を利用したWindowsでも同じ。

/etc/sysctl.d/99-sysctl.confファイルに下記を追記する。
```
vm.max_map_count = 262144
```
その後、以下のコマンドを実行する。
```
$ sudo sysctl --system
```

### batchプログラムのビルドを行う
以下のコマンドを実行する。
docker-composeコマンドをsudoで実行する場合は-Eオプションを付けて現在の環境変数をそのまま保持すること。
```
$ sudo -E docker-compose run --rm batch ./gradlew bootJar
```

## ベンチマークプログラムの実行
まず最初に以下のコマンドによりSRA metadataの取得を行う。
```
$ sudo -E docker-compose run --rm -e ACTION=getSRA -e DATE=20200104 batch
```
elasticsearchコンテナが起動し終わってbatchプログラムから接続できるようになるまで、curlのエラーが表示される。

running timeで表示される時間は、NCBI_SRA_Metadata_Full_yyyymmdd.tar.gz をダウンロードしてtar.gzファイルを展開するのに要した時間である。

ダウンロードに要した時間、tar.gzファイルを展開するのに要した時間を個別に取得するにはログのタイムスタンプから計算すること。

次に、以下のコマンドにより data/public/sra/full/accessions/SRA_Accessions.tab の内容をPostgreSQLデータベースに取り込む。
```
$ sudo -E docker-compose run --rm -e ACTION=registerSRAAccessions -e DATE=20200104 batch
```

最後に、以下のコマンドによりNCBI_SRA_Metadata_Full_yyyymmdd.tar.gzから展開されたxmlファイルをElasticSearchに取り込む処理を走らせ、この処理にかかる時間を計測する。実行時間は実行結果中に `Spend time(minute):635.04294510055`（分単位） および `StopWatch '': running time = 38102576706033 ns`（ns単位） のように出力される。
```
$ sudo -E docker-compose run --rm -e ACTION=registerSRA -e DATE=20200104 batch
Starting ddbjld_public_db_stage               ... done
Starting ddbj-ld-stage_elasticsearch2_stage_1 ... done
Starting ddbj-ld-stage_elasticsearch_stage_1  ... done
ACTION: registerSRA
DATE: 20200104
ENV:
（中略）
2022-06-24 06:47:27.077  INFO 8 --- [           main] com.ddbj.ld.DDBJApplication              : Start registering SRA's data...
2022-06-24 06:47:28.620  INFO 8 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2022-06-24 06:47:28.744  INFO 8 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2022-06-24 17:22:29.651  INFO 8 --- [           main] com.ddbj.ld.DDBJApplication              : Complete registering SRA's data.
2022-06-24 17:22:29.656  INFO 8 --- [           main] com.ddbj.ld.DDBJApplication              : Spend time(minute):635.04294510055
2022-06-24 17:22:29.657  INFO 8 --- [           main] com.ddbj.ld.DDBJApplication              : StopWatch '': running time = 38102576706033 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
38102576706033  100%  

2022-06-24 17:22:29.671  INFO 8 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2022-06-24 17:22:29.680  INFO 8 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
$
```

## 実行結果
使用するデータは https://ftp.ncbi.nih.gov/sra/reports/Metadata/ からダウンロードされる。DATE=20220519の場合は7.7GB, DATE=20200104の場合は2.5GBのデータをダウンロードして使用する。
### [遺伝研Thin計算ノード Type 1a](https://sc.ddbj.nig.ac.jp/guides/hardware#thin-%E8%A8%88%E7%AE%97%E3%83%8E%E3%83%BC%E3%83%89-type-1a-hpe-proliant-dl385-gen10-136-%E5%8F%B0) (ストレージは3.2TB NVMe SSDを使用) での実行結果

ACTION|DATE=20220519|DATE=20200104
---|---|---
getSRA|7.7GB download 7m 59s + extract 32m 54s|2.5GB download 2m 30s + extract 19m 43s
registerSRAAccessions|1h 27m 48s|40m 1s
registerSRA|38h 0m 2s|10h 35m 2s

### 32GBメモリPC（ストレージは3TB SATA3 HDDを使用）での実行結果

※docker-compose.ymlにてelasticsearch, elasticsearch2のメモリ割り当てを ES_JAVA_OPTS: "-Xmx10g -Xms10g" に設定

ACTION|DATE=20220519
---|---
getSRA|7.7GB download 11min 6s + extract 1h 39min 16s
registerSRAAccessions|2h 12min 7s
registerSRA|71h 33min 42s

======= ベンチマークについての説明はここまで =======








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

## 6. Dockerのコンテナで実行してIntelliJからリモートデバッグ

- 詳細は下記参照

[参考記事1](https://stackoverflow.com/questions/57395597/intellij-debug-java-application-in-docker)
[参考記事2](https://blog.jetbrains.com/idea/2019/04/debug-your-java-applications-in-docker-using-intellij-idea/)
[参考記事3](https://stackoverflow.com/questions/52092504/spring-boot-bootrun-with-continuous-build)

## 7. Appendix

### 7-1. Running Docker is by root user or user has sudo or user belong to docker group
<https://docs.docker.com/install/linux/docker-ce/centos/#install-using-the-convenience-script>

### 7-2. Elasticsearch in Docker need least ddbj-ld memory size
<https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#_set_vm_max_map_count_to_at_least_262144>

## 8. Memo

- localで動かすときはdockerのメモリを4GB以上割り当てる
