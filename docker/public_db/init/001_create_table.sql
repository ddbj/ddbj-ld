CREATE TABLE t_bioproject_biosample
(
  bioproject_accession varchar(14) NOT NULL,
  biosample_accession  varchar(14) NOT NULL,
  created_at           timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at           timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (bioproject_accession, biosample_accession)
);

COMMENT ON TABLE t_bioproject_biosample IS 'バイオプロジェクトバイオサンプル';

COMMENT ON COLUMN t_bioproject_biosample.bioproject_accession IS 'バイオプロジェクトアクセッション';

COMMENT ON COLUMN t_bioproject_biosample.biosample_accession IS 'バイオサンプルアクセッション';

COMMENT ON COLUMN t_bioproject_biosample.created_at IS '作成日時';

COMMENT ON COLUMN t_bioproject_biosample.updated_at IS '更新日時';

CREATE TABLE t_dra_analysis
(
  accession  varchar(14) NOT NULL,
  submission varchar(14) NOT NULL,
  status     varchar(11) NOT NULL,
  updated    timestamp  ,
  published  timestamp  ,
  received   timestamp  ,
  type       varchar(10) NOT NULL,
  center     text       ,
  visibility varchar(17) NOT NULL,
  alias      text       ,
  experiment varchar(14),
  sample     varchar(14),
  study      varchar(14),
  loaded     smallint   ,
  spots      text       ,
  bases      text       ,
  md5sum     varchar(32),
  biosample  varchar(14),
  bioproject varchar(14),
  replacedby text       ,
  created_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (accession)
);

COMMENT ON TABLE t_dra_analysis IS 'DRAアナリシス';

COMMENT ON COLUMN t_dra_analysis.accession IS 'アクセッション';

COMMENT ON COLUMN t_dra_analysis.submission IS 'サブミッション';

COMMENT ON COLUMN t_dra_analysis.status IS 'ステータス';

COMMENT ON COLUMN t_dra_analysis.updated IS 'データ更新日';

COMMENT ON COLUMN t_dra_analysis.published IS 'データ公開日';

COMMENT ON COLUMN t_dra_analysis.received IS 'データ受信日';

COMMENT ON COLUMN t_dra_analysis.type IS 'データ種別';

COMMENT ON COLUMN t_dra_analysis.center IS 'センター';

COMMENT ON COLUMN t_dra_analysis.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_dra_analysis.alias IS 'エイリアス';

COMMENT ON COLUMN t_dra_analysis.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_dra_analysis.sample IS 'サンプル';

COMMENT ON COLUMN t_dra_analysis.study IS 'スタディ';

COMMENT ON COLUMN t_dra_analysis.loaded IS 'ローデッド';

COMMENT ON COLUMN t_dra_analysis.spots IS 'スポット';

COMMENT ON COLUMN t_dra_analysis.bases IS 'ベース';

COMMENT ON COLUMN t_dra_analysis.md5sum IS 'md5値';

COMMENT ON COLUMN t_dra_analysis.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_dra_analysis.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_dra_analysis.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_dra_analysis.created_at IS '作成日時';

COMMENT ON COLUMN t_dra_analysis.updated_at IS '更新日時';

CREATE TABLE t_dra_experiment
(
  accession  varchar(14) NOT NULL,
  submission varchar(14) NOT NULL,
  status     varchar(11) NOT NULL,
  updated    timestamp  ,
  published  timestamp  ,
  received   timestamp  ,
  type       varchar(10) NOT NULL,
  center     text       ,
  visibility varchar(17) NOT NULL,
  alias      text       ,
  experiment varchar(14),
  sample     varchar(14),
  study      varchar(14),
  loaded     smallint   ,
  spots      text       ,
  bases      text       ,
  md5sum     varchar(32),
  biosample  varchar(14),
  bioproject varchar(14),
  replacedby text       ,
  created_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (accession)
);

COMMENT ON TABLE t_dra_experiment IS 'DRAエクスペリメント';

COMMENT ON COLUMN t_dra_experiment.accession IS 'アクセッション';

COMMENT ON COLUMN t_dra_experiment.submission IS 'サブミッション';

COMMENT ON COLUMN t_dra_experiment.status IS 'ステータス';

COMMENT ON COLUMN t_dra_experiment.updated IS 'データ更新日';

COMMENT ON COLUMN t_dra_experiment.published IS 'データ公開日';

COMMENT ON COLUMN t_dra_experiment.received IS 'データ受信日';

COMMENT ON COLUMN t_dra_experiment.type IS 'データ種別';

COMMENT ON COLUMN t_dra_experiment.center IS 'センター';

COMMENT ON COLUMN t_dra_experiment.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_dra_experiment.alias IS 'エイリアス';

COMMENT ON COLUMN t_dra_experiment.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_dra_experiment.sample IS 'サンプル';

COMMENT ON COLUMN t_dra_experiment.study IS 'スタディ';

COMMENT ON COLUMN t_dra_experiment.loaded IS 'ローデッド';

COMMENT ON COLUMN t_dra_experiment.spots IS 'スポット';

COMMENT ON COLUMN t_dra_experiment.bases IS 'ベース';

COMMENT ON COLUMN t_dra_experiment.md5sum IS 'md5値';

COMMENT ON COLUMN t_dra_experiment.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_dra_experiment.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_dra_experiment.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_dra_experiment.created_at IS '作成日時';

COMMENT ON COLUMN t_dra_experiment.updated_at IS '更新日時';

CREATE TABLE t_dra_run
(
  accession  varchar(14) NOT NULL,
  submission varchar(14) NOT NULL,
  status     varchar(11) NOT NULL,
  updated    timestamp  ,
  published  timestamp  ,
  received   timestamp  ,
  type       varchar(10) NOT NULL,
  center     text       ,
  visibility varchar(17) NOT NULL,
  alias      text       ,
  experiment varchar(14),
  sample     varchar(14),
  study      varchar(14),
  loaded     smallint   ,
  spots      text       ,
  bases      text       ,
  md5sum     varchar(32),
  biosample  varchar(14),
  bioproject varchar(14),
  replacedby text       ,
  created_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (accession)
);

COMMENT ON TABLE t_dra_run IS 'DRAラン';

COMMENT ON COLUMN t_dra_run.accession IS 'アクセッション';

COMMENT ON COLUMN t_dra_run.submission IS 'サブミッション';

COMMENT ON COLUMN t_dra_run.status IS 'ステータス';

COMMENT ON COLUMN t_dra_run.updated IS 'データ更新日';

COMMENT ON COLUMN t_dra_run.published IS 'データ公開日';

COMMENT ON COLUMN t_dra_run.received IS 'データ受信日';

COMMENT ON COLUMN t_dra_run.type IS 'データ種別';

COMMENT ON COLUMN t_dra_run.center IS 'センター';

COMMENT ON COLUMN t_dra_run.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_dra_run.alias IS 'エイリアス';

COMMENT ON COLUMN t_dra_run.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_dra_run.sample IS 'サンプル';

COMMENT ON COLUMN t_dra_run.study IS 'スタディ';

COMMENT ON COLUMN t_dra_run.loaded IS 'ローデッド';

COMMENT ON COLUMN t_dra_run.spots IS 'スポット';

COMMENT ON COLUMN t_dra_run.bases IS 'ベース';

COMMENT ON COLUMN t_dra_run.md5sum IS 'md5値';

COMMENT ON COLUMN t_dra_run.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_dra_run.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_dra_run.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_dra_run.created_at IS '作成日時';

COMMENT ON COLUMN t_dra_run.updated_at IS '更新日時';

CREATE TABLE t_dra_sample
(
  accession  varchar(14) NOT NULL,
  submission varchar(14) NOT NULL,
  status     varchar(11) NOT NULL,
  updated    timestamp  ,
  published  timestamp  ,
  received   timestamp  ,
  type       varchar(10) NOT NULL,
  center     text       ,
  visibility varchar(17) NOT NULL,
  alias      text       ,
  experiment varchar(14),
  sample     varchar(14),
  study      varchar(14),
  loaded     smallint   ,
  spots      text       ,
  bases      text       ,
  md5sum     varchar(32),
  biosample  varchar(14),
  bioproject varchar(14),
  replacedby text       ,
  created_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (accession)
);

COMMENT ON TABLE t_dra_sample IS 'DRAサンプル';

COMMENT ON COLUMN t_dra_sample.accession IS 'アクセッション';

COMMENT ON COLUMN t_dra_sample.submission IS 'サブミッション';

COMMENT ON COLUMN t_dra_sample.status IS 'ステータス';

COMMENT ON COLUMN t_dra_sample.updated IS 'データ更新日';

COMMENT ON COLUMN t_dra_sample.published IS 'データ公開日';

COMMENT ON COLUMN t_dra_sample.received IS 'データ受信日';

COMMENT ON COLUMN t_dra_sample.type IS 'データ種別';

COMMENT ON COLUMN t_dra_sample.center IS 'センター';

COMMENT ON COLUMN t_dra_sample.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_dra_sample.alias IS 'エイリアス';

COMMENT ON COLUMN t_dra_sample.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_dra_sample.sample IS 'サンプル';

COMMENT ON COLUMN t_dra_sample.study IS 'スタディ';

COMMENT ON COLUMN t_dra_sample.loaded IS 'ローデッド';

COMMENT ON COLUMN t_dra_sample.spots IS 'スポット';

COMMENT ON COLUMN t_dra_sample.bases IS 'ベース';

COMMENT ON COLUMN t_dra_sample.md5sum IS 'md5値';

COMMENT ON COLUMN t_dra_sample.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_dra_sample.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_dra_sample.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_dra_sample.created_at IS '作成日時';

COMMENT ON COLUMN t_dra_sample.updated_at IS '更新日時';

CREATE TABLE t_dra_study
(
  accession  varchar(14) NOT NULL,
  submission varchar(14) NOT NULL,
  status     varchar(11) NOT NULL,
  updated    timestamp  ,
  published  timestamp  ,
  received   timestamp  ,
  type       varchar(10) NOT NULL,
  center     text       ,
  visibility varchar(17) NOT NULL,
  alias      text       ,
  experiment varchar(14),
  sample     varchar(14),
  study      varchar(14),
  loaded     smallint   ,
  spots      text       ,
  bases      text       ,
  md5sum     varchar(32),
  biosample  varchar(14),
  bioproject varchar(14),
  replacedby text       ,
  created_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (accession)
);

COMMENT ON TABLE t_dra_study IS 'DRAスタディ';

COMMENT ON COLUMN t_dra_study.accession IS 'アクセッション';

COMMENT ON COLUMN t_dra_study.submission IS 'サブミッション';

COMMENT ON COLUMN t_dra_study.status IS 'ステータス';

COMMENT ON COLUMN t_dra_study.updated IS 'データ更新日';

COMMENT ON COLUMN t_dra_study.published IS 'データ公開日';

COMMENT ON COLUMN t_dra_study.received IS 'データ受信日';

COMMENT ON COLUMN t_dra_study.type IS 'データ種別';

COMMENT ON COLUMN t_dra_study.center IS 'センター';

COMMENT ON COLUMN t_dra_study.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_dra_study.alias IS 'エイリアス';

COMMENT ON COLUMN t_dra_study.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_dra_study.sample IS 'サンプル';

COMMENT ON COLUMN t_dra_study.study IS 'スタディ';

COMMENT ON COLUMN t_dra_study.loaded IS 'ローデッド';

COMMENT ON COLUMN t_dra_study.spots IS 'スポット';

COMMENT ON COLUMN t_dra_study.bases IS 'ベース';

COMMENT ON COLUMN t_dra_study.md5sum IS 'md5値';

COMMENT ON COLUMN t_dra_study.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_dra_study.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_dra_study.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_dra_study.created_at IS '作成日時';

COMMENT ON COLUMN t_dra_study.updated_at IS '更新日時';

CREATE TABLE t_dra_submission
(
  accession  varchar(14) NOT NULL,
  submission varchar(14) NOT NULL,
  status     varchar(11) NOT NULL,
  updated    timestamp  ,
  published  timestamp  ,
  received   timestamp  ,
  type       varchar(10) NOT NULL,
  center     text       ,
  visibility varchar(17) NOT NULL,
  alias      text       ,
  experiment varchar(14),
  sample     varchar(14),
  study      varchar(14),
  loaded     smallint   ,
  spots      text       ,
  bases      text       ,
  md5sum     varchar(32),
  biosample  varchar(14),
  bioproject varchar(14),
  replacedby text       ,
  created_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (accession)
);

COMMENT ON TABLE t_dra_submission IS 'DRAサブミッション';

COMMENT ON COLUMN t_dra_submission.accession IS 'アクセッション';

COMMENT ON COLUMN t_dra_submission.submission IS 'サブミッション';

COMMENT ON COLUMN t_dra_submission.status IS 'ステータス';

COMMENT ON COLUMN t_dra_submission.updated IS 'データ更新日';

COMMENT ON COLUMN t_dra_submission.published IS 'データ公開日';

COMMENT ON COLUMN t_dra_submission.received IS 'データ受信日';

COMMENT ON COLUMN t_dra_submission.type IS 'データ種別';

COMMENT ON COLUMN t_dra_submission.center IS 'センター';

COMMENT ON COLUMN t_dra_submission.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_dra_submission.alias IS 'エイリアス';

COMMENT ON COLUMN t_dra_submission.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_dra_submission.sample IS 'サンプル';

COMMENT ON COLUMN t_dra_submission.study IS 'スタディ';

COMMENT ON COLUMN t_dra_submission.loaded IS 'ローデッド';

COMMENT ON COLUMN t_dra_submission.spots IS 'スポット';

COMMENT ON COLUMN t_dra_submission.bases IS 'ベース';

COMMENT ON COLUMN t_dra_submission.md5sum IS 'md5値';

COMMENT ON COLUMN t_dra_submission.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_dra_submission.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_dra_submission.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_dra_submission.created_at IS '作成日時';

COMMENT ON COLUMN t_dra_submission.updated_at IS '更新日時';

CREATE TABLE t_jga_analysis_study
(
  analysis_accession varchar(14) NOT NULL,
  study_accession    varchar(14) NOT NULL,
  created_at         timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at         timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (analysis_accession, study_accession)
);

COMMENT ON TABLE t_jga_analysis_study IS 'JGAアナリシススタディ関係情報';

COMMENT ON COLUMN t_jga_analysis_study.analysis_accession IS 'アナリシスアクセッション';

COMMENT ON COLUMN t_jga_analysis_study.study_accession IS 'スタディアクセッション';

COMMENT ON COLUMN t_jga_analysis_study.created_at IS '作成日時';

COMMENT ON COLUMN t_jga_analysis_study.updated_at IS '更新日時';

CREATE TABLE t_jga_data_experiment
(
  data_accession       varchar(14) NOT NULL,
  experiment_accession varchar(14) NOT NULL,
  created_at           timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at           timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (data_accession, experiment_accession)
);

COMMENT ON TABLE t_jga_data_experiment IS 'JGAデータエクスペリメント関係情報';

COMMENT ON COLUMN t_jga_data_experiment.data_accession IS 'データアクセッション';

COMMENT ON COLUMN t_jga_data_experiment.experiment_accession IS 'エクスペリメントアクセッション';

COMMENT ON COLUMN t_jga_data_experiment.created_at IS '作成日時';

COMMENT ON COLUMN t_jga_data_experiment.updated_at IS '更新日時';

CREATE TABLE t_jga_dataset_analysis
(
  dataset_accession  varchar(14) NOT NULL,
  analysis_accession varchar(14) NOT NULL,
  created_at         timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at         timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (dataset_accession, analysis_accession)
);

COMMENT ON TABLE t_jga_dataset_analysis IS 'JGAデータセットアナリシス関係情報';

COMMENT ON COLUMN t_jga_dataset_analysis.dataset_accession IS 'データセットアクセッション';

COMMENT ON COLUMN t_jga_dataset_analysis.analysis_accession IS 'アナリシスアクセッション';

COMMENT ON COLUMN t_jga_dataset_analysis.created_at IS '作成日時';

COMMENT ON COLUMN t_jga_dataset_analysis.updated_at IS '更新日時';

CREATE TABLE t_jga_dataset_data
(
  dataset_accession varchar(14) NOT NULL,
  data_accession    varchar(14) NOT NULL,
  created_at        timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at        timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (dataset_accession, data_accession)
);

COMMENT ON TABLE t_jga_dataset_data IS 'JGAデータセットデータ関係情報';

COMMENT ON COLUMN t_jga_dataset_data.dataset_accession IS 'データセットアクセッション';

COMMENT ON COLUMN t_jga_dataset_data.data_accession IS 'データアクセッション';

COMMENT ON COLUMN t_jga_dataset_data.created_at IS '作成日時';

COMMENT ON COLUMN t_jga_dataset_data.updated_at IS '更新日時';

CREATE TABLE t_jga_dataset_policy
(
  dataset_accession varchar(14) NOT NULL,
  policy_accession  varchar(14) NOT NULL,
  created_at        timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at        timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (dataset_accession, policy_accession)
);

COMMENT ON TABLE t_jga_dataset_policy IS 'JGAデータセットポリシー関係情報';

COMMENT ON COLUMN t_jga_dataset_policy.dataset_accession IS 'データセットアクセッション';

COMMENT ON COLUMN t_jga_dataset_policy.policy_accession IS 'ポリシーアクセッション';

COMMENT ON COLUMN t_jga_dataset_policy.created_at IS '作成日時';

COMMENT ON COLUMN t_jga_dataset_policy.updated_at IS '更新日時';

CREATE TABLE t_jga_date
(
  accession      varchar(14) NOT NULL,
  date_created   timestamp   NOT NULL,
  date_published timestamp   NOT NULL,
  date_modified  timestamp   NOT NULL,
  created_at     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (accession)
);

COMMENT ON TABLE t_jga_date IS 'JGA日付情報';

COMMENT ON COLUMN t_jga_date.accession IS 'アクセッション';

COMMENT ON COLUMN t_jga_date.date_created IS 'データ作成日';

COMMENT ON COLUMN t_jga_date.date_published IS 'データ公開日';

COMMENT ON COLUMN t_jga_date.date_modified IS 'データ更新日';

COMMENT ON COLUMN t_jga_date.created_at IS '作成日時';

COMMENT ON COLUMN t_jga_date.updated_at IS '更新日時';

CREATE TABLE t_jga_experiment_study
(
  experiment_accession varchar(14) NOT NULL,
  study_accession      varchar(14) NOT NULL,
  created_at           timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at           timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (experiment_accession, study_accession)
);

COMMENT ON TABLE t_jga_experiment_study IS 'JGAエクスペリメントスタディ関係情報';

COMMENT ON COLUMN t_jga_experiment_study.experiment_accession IS 'エクスペリメントアクセッション';

COMMENT ON COLUMN t_jga_experiment_study.study_accession IS 'スタディアクセッション';

COMMENT ON COLUMN t_jga_experiment_study.created_at IS '作成日時';

COMMENT ON COLUMN t_jga_experiment_study.updated_at IS '更新日時';

CREATE TABLE t_jga_policy_dac
(
  policy_accession varchar(14) NOT NULL,
  dac_accession    varchar(14) NOT NULL,
  created_at       timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at       timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (policy_accession, dac_accession)
);

COMMENT ON TABLE t_jga_policy_dac IS 'JGAポリシーダック';

COMMENT ON COLUMN t_jga_policy_dac.policy_accession IS 'ポリシーアクセッション';

COMMENT ON COLUMN t_jga_policy_dac.dac_accession IS 'ダックアクセッション';

COMMENT ON COLUMN t_jga_policy_dac.created_at IS '作成日時';

COMMENT ON COLUMN t_jga_policy_dac.updated_at IS '更新日時';
