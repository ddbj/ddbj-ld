# 多言語化ファイル

`react-intl` を用いた多言語化用の文言(Message)を管理する方法。

`id` と各言語ごとの文言をCSV形式のファイル（以下、対応表）で管理する。

## 依存パッケージ

多言語化ファイルの操作に関する

## 対応表の書式

対応表の書式は以下の通り

```
"key","ja","en"
"message.1","文言 1","Message 1"
"message.2","文言 2","Message 2"
"message.3","文言 3","Message 3"
```

## 対応表の生成

`config.source` で指定されたファイルを `@formatjs/cli` を用いて対応表を生成する。

また、`/src/intl/{lang}.json` において既に定義されている文言はCSVに追加する。

ソースコード内に文言を利用する場合は、`config.additional_message_ids` 内に追記すること。

```
npm run intl:extract
```

## 多言語化ファイルの生成

`config.map_file` においての定義を元に多言語化ファイルを生成する。

`/src/intl/{lang}.json` において **既に定義されている文言は無視** される。

```sh
npm run intl:compile
```

## 対応表と多言語化ファイルの整合性について

対応表と多言語化ファイルの生成おいて、いずれの動作も生成のもととなる情報のみに依存するようにする。

## 設定ファイル

以下のパスで設定が可能

```
scripts/intl/config.json
```

|key|説明|
|--|--|
|map_file|対応表の出力パス|
|intl_directory|多言語化ファイルを出力するディレクトリ|
|source|対象とするソースコード。glob形式|
|languages|言語|
|additional_message_ids|ソースコード上に存在しない文言ID|
