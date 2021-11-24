

CREATE TABLE t_bioproject
(
    accession      varchar(14) NOT NULL,
    status         varchar(11) NOT NULL,
    visibility     text        NOT NULL,
    date_created   timestamp  ,
    date_published timestamp  ,
    date_modified  timestamp  ,
    json           text        NOT NULL,
    created_at     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (accession)
);

COMMENT ON TABLE t_bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_bioproject.accession IS 'アクセッション';

COMMENT ON COLUMN t_bioproject.status IS 'ステータス';

COMMENT ON COLUMN t_bioproject.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_bioproject.date_created IS 'データ作成日';

COMMENT ON COLUMN t_bioproject.date_published IS 'データ公開日';

COMMENT ON COLUMN t_bioproject.date_modified IS 'データ更新日';

COMMENT ON COLUMN t_bioproject.json IS 'json';

COMMENT ON COLUMN t_bioproject.created_at IS '作成日時';

COMMENT ON COLUMN t_bioproject.updated_at IS '更新日時';

CREATE TABLE t_biosample
(
    accession      varchar(14) NOT NULL,
    status         varchar(11) NOT NULL,
    visibility     text        NOT NULL,
    date_created   timestamp  ,
    date_published timestamp  ,
    date_modified  timestamp  ,
    json           text        NOT NULL,
    created_at     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (accession)
);

COMMENT ON TABLE t_biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_biosample.accession IS 'アクセッション';

COMMENT ON COLUMN t_biosample.status IS 'ステータス';

COMMENT ON COLUMN t_biosample.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_biosample.date_created IS 'データ作成日';

COMMENT ON COLUMN t_biosample.date_published IS 'データ公開日';

COMMENT ON COLUMN t_biosample.date_modified IS 'データ更新日';

COMMENT ON COLUMN t_biosample.json IS 'json';

COMMENT ON COLUMN t_biosample.created_at IS '作成日時';

COMMENT ON COLUMN t_biosample.updated_at IS '更新日時';

CREATE TABLE t_dra_accession
(
    accession  varchar(14) NOT NULL,
    submission varchar(14) NOT NULL,
    status     varchar(11) NOT NULL,
    updated    timestamp  ,
    published  timestamp  ,
    received   timestamp  ,
    type       varchar(10) NOT NULL,
    center     text       ,
    visibility text        NOT NULL,
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
    PRIMARY KEY (accession, submission)
);

COMMENT ON TABLE t_dra_accession IS 'DRAアクセッションズ';

COMMENT ON COLUMN t_dra_accession.accession IS 'アクセッション';

COMMENT ON COLUMN t_dra_accession.submission IS 'サブミッション';

COMMENT ON COLUMN t_dra_accession.status IS 'ステータス';

COMMENT ON COLUMN t_dra_accession.updated IS 'データ更新日';

COMMENT ON COLUMN t_dra_accession.published IS 'データ公開日';

COMMENT ON COLUMN t_dra_accession.received IS 'データ受信日';

COMMENT ON COLUMN t_dra_accession.type IS 'データ種別';

COMMENT ON COLUMN t_dra_accession.center IS 'センター';

COMMENT ON COLUMN t_dra_accession.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_dra_accession.alias IS 'エイリアス';

COMMENT ON COLUMN t_dra_accession.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_dra_accession.sample IS 'サンプル';

COMMENT ON COLUMN t_dra_accession.study IS 'スタディ';

COMMENT ON COLUMN t_dra_accession.loaded IS 'ローデッド';

COMMENT ON COLUMN t_dra_accession.spots IS 'スポット';

COMMENT ON COLUMN t_dra_accession.bases IS 'ベース';

COMMENT ON COLUMN t_dra_accession.md5sum IS 'md5値';

COMMENT ON COLUMN t_dra_accession.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_dra_accession.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_dra_accession.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_dra_accession.created_at IS '作成日時';

COMMENT ON COLUMN t_dra_accession.updated_at IS '更新日時';

CREATE TABLE t_dra_livelist
(
    accession  varchar(14) NOT NULL,
    submission varchar(14) NOT NULL,
    visibility text        NOT NULL,
    updated    timestamp  ,
    type       varchar(10) NOT NULL,
    center     text       ,
    alias      text       ,
    md5sum     varchar(32),
    created_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (accession)
);

COMMENT ON TABLE t_dra_livelist IS 'DRAライブリスト';

COMMENT ON COLUMN t_dra_livelist.accession IS 'アクセッション';

COMMENT ON COLUMN t_dra_livelist.submission IS 'サブミッション';

COMMENT ON COLUMN t_dra_livelist.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_dra_livelist.updated IS 'データ更新日';

COMMENT ON COLUMN t_dra_livelist.type IS 'データ種別';

COMMENT ON COLUMN t_dra_livelist.center IS 'センター';

COMMENT ON COLUMN t_dra_livelist.alias IS 'エイリアス';

COMMENT ON COLUMN t_dra_livelist.md5sum IS 'md5値';

COMMENT ON COLUMN t_dra_livelist.created_at IS '作成日時';

COMMENT ON COLUMN t_dra_livelist.updated_at IS '更新日時';

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

CREATE TABLE t_sra_analysis
(
    accession  varchar(14) NOT NULL,
    submission varchar(14) NOT NULL,
    status     varchar(11) NOT NULL,
    updated    timestamp  ,
    published  timestamp  ,
    received   timestamp  ,
    type       varchar(10) NOT NULL,
    center     text       ,
    visibility text        NOT NULL,
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

COMMENT ON TABLE t_sra_analysis IS 'SRAアナリシス';

COMMENT ON COLUMN t_sra_analysis.accession IS 'アクセッション';

COMMENT ON COLUMN t_sra_analysis.submission IS 'サブミッション';

COMMENT ON COLUMN t_sra_analysis.status IS 'ステータス';

COMMENT ON COLUMN t_sra_analysis.updated IS 'データ更新日';

COMMENT ON COLUMN t_sra_analysis.published IS 'データ公開日';

COMMENT ON COLUMN t_sra_analysis.received IS 'データ受信日';

COMMENT ON COLUMN t_sra_analysis.type IS 'データ種別';

COMMENT ON COLUMN t_sra_analysis.center IS 'センター';

COMMENT ON COLUMN t_sra_analysis.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_sra_analysis.alias IS 'エイリアス';

COMMENT ON COLUMN t_sra_analysis.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_sra_analysis.sample IS 'サンプル';

COMMENT ON COLUMN t_sra_analysis.study IS 'スタディ';

COMMENT ON COLUMN t_sra_analysis.loaded IS 'ローデッド';

COMMENT ON COLUMN t_sra_analysis.spots IS 'スポット';

COMMENT ON COLUMN t_sra_analysis.bases IS 'ベース';

COMMENT ON COLUMN t_sra_analysis.md5sum IS 'md5値';

COMMENT ON COLUMN t_sra_analysis.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_sra_analysis.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_sra_analysis.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_sra_analysis.created_at IS '作成日時';

COMMENT ON COLUMN t_sra_analysis.updated_at IS '更新日時';

CREATE TABLE t_sra_experiment
(
    accession  varchar(14) NOT NULL,
    submission varchar(14) NOT NULL,
    status     varchar(11) NOT NULL,
    updated    timestamp  ,
    published  timestamp  ,
    received   timestamp  ,
    type       varchar(10) NOT NULL,
    center     text       ,
    visibility text        NOT NULL,
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

COMMENT ON TABLE t_sra_experiment IS 'SRAエクスペリメント';

COMMENT ON COLUMN t_sra_experiment.accession IS 'アクセッション';

COMMENT ON COLUMN t_sra_experiment.submission IS 'サブミッション';

COMMENT ON COLUMN t_sra_experiment.status IS 'ステータス';

COMMENT ON COLUMN t_sra_experiment.updated IS 'データ更新日';

COMMENT ON COLUMN t_sra_experiment.published IS 'データ公開日';

COMMENT ON COLUMN t_sra_experiment.received IS 'データ受信日';

COMMENT ON COLUMN t_sra_experiment.type IS 'データ種別';

COMMENT ON COLUMN t_sra_experiment.center IS 'センター';

COMMENT ON COLUMN t_sra_experiment.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_sra_experiment.alias IS 'エイリアス';

COMMENT ON COLUMN t_sra_experiment.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_sra_experiment.sample IS 'サンプル';

COMMENT ON COLUMN t_sra_experiment.study IS 'スタディ';

COMMENT ON COLUMN t_sra_experiment.loaded IS 'ローデッド';

COMMENT ON COLUMN t_sra_experiment.spots IS 'スポット';

COMMENT ON COLUMN t_sra_experiment.bases IS 'ベース';

COMMENT ON COLUMN t_sra_experiment.md5sum IS 'md5値';

COMMENT ON COLUMN t_sra_experiment.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_sra_experiment.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_sra_experiment.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_sra_experiment.created_at IS '作成日時';

COMMENT ON COLUMN t_sra_experiment.updated_at IS '更新日時';

CREATE TABLE t_sra_run
(
    accession  varchar(14) NOT NULL,
    submission varchar(14) NOT NULL,
    status     varchar(11) NOT NULL,
    updated    timestamp  ,
    published  timestamp  ,
    received   timestamp  ,
    type       varchar(10) NOT NULL,
    center     text       ,
    visibility text        NOT NULL,
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

COMMENT ON TABLE t_sra_run IS 'SRAラン';

COMMENT ON COLUMN t_sra_run.accession IS 'アクセッション';

COMMENT ON COLUMN t_sra_run.submission IS 'サブミッション';

COMMENT ON COLUMN t_sra_run.status IS 'ステータス';

COMMENT ON COLUMN t_sra_run.updated IS 'データ更新日';

COMMENT ON COLUMN t_sra_run.published IS 'データ公開日';

COMMENT ON COLUMN t_sra_run.received IS 'データ受信日';

COMMENT ON COLUMN t_sra_run.type IS 'データ種別';

COMMENT ON COLUMN t_sra_run.center IS 'センター';

COMMENT ON COLUMN t_sra_run.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_sra_run.alias IS 'エイリアス';

COMMENT ON COLUMN t_sra_run.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_sra_run.sample IS 'サンプル';

COMMENT ON COLUMN t_sra_run.study IS 'スタディ';

COMMENT ON COLUMN t_sra_run.loaded IS 'ローデッド';

COMMENT ON COLUMN t_sra_run.spots IS 'スポット';

COMMENT ON COLUMN t_sra_run.bases IS 'ベース';

COMMENT ON COLUMN t_sra_run.md5sum IS 'md5値';

COMMENT ON COLUMN t_sra_run.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_sra_run.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_sra_run.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_sra_run.created_at IS '作成日時';

COMMENT ON COLUMN t_sra_run.updated_at IS '更新日時';

CREATE TABLE t_sra_sample
(
    accession  varchar(14) NOT NULL,
    submission varchar(14) NOT NULL,
    status     varchar(11) NOT NULL,
    updated    timestamp  ,
    published  timestamp  ,
    received   timestamp  ,
    type       varchar(10) NOT NULL,
    center     text       ,
    visibility text        NOT NULL,
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

COMMENT ON TABLE t_sra_sample IS 'SRAサンプル';

COMMENT ON COLUMN t_sra_sample.accession IS 'アクセッション';

COMMENT ON COLUMN t_sra_sample.submission IS 'サブミッション';

COMMENT ON COLUMN t_sra_sample.status IS 'ステータス';

COMMENT ON COLUMN t_sra_sample.updated IS 'データ更新日';

COMMENT ON COLUMN t_sra_sample.published IS 'データ公開日';

COMMENT ON COLUMN t_sra_sample.received IS 'データ受信日';

COMMENT ON COLUMN t_sra_sample.type IS 'データ種別';

COMMENT ON COLUMN t_sra_sample.center IS 'センター';

COMMENT ON COLUMN t_sra_sample.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_sra_sample.alias IS 'エイリアス';

COMMENT ON COLUMN t_sra_sample.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_sra_sample.sample IS 'サンプル';

COMMENT ON COLUMN t_sra_sample.study IS 'スタディ';

COMMENT ON COLUMN t_sra_sample.loaded IS 'ローデッド';

COMMENT ON COLUMN t_sra_sample.spots IS 'スポット';

COMMENT ON COLUMN t_sra_sample.bases IS 'ベース';

COMMENT ON COLUMN t_sra_sample.md5sum IS 'md5値';

COMMENT ON COLUMN t_sra_sample.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_sra_sample.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_sra_sample.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_sra_sample.created_at IS '作成日時';

COMMENT ON COLUMN t_sra_sample.updated_at IS '更新日時';

CREATE TABLE t_sra_study
(
    accession  varchar(14) NOT NULL,
    submission varchar(14) NOT NULL,
    status     varchar(11) NOT NULL,
    updated    timestamp  ,
    published  timestamp  ,
    received   timestamp  ,
    type       varchar(10) NOT NULL,
    center     text       ,
    visibility text        NOT NULL,
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

COMMENT ON TABLE t_sra_study IS 'SRAスタディ';

COMMENT ON COLUMN t_sra_study.accession IS 'アクセッション';

COMMENT ON COLUMN t_sra_study.submission IS 'サブミッション';

COMMENT ON COLUMN t_sra_study.status IS 'ステータス';

COMMENT ON COLUMN t_sra_study.updated IS 'データ更新日';

COMMENT ON COLUMN t_sra_study.published IS 'データ公開日';

COMMENT ON COLUMN t_sra_study.received IS 'データ受信日';

COMMENT ON COLUMN t_sra_study.type IS 'データ種別';

COMMENT ON COLUMN t_sra_study.center IS 'センター';

COMMENT ON COLUMN t_sra_study.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_sra_study.alias IS 'エイリアス';

COMMENT ON COLUMN t_sra_study.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_sra_study.sample IS 'サンプル';

COMMENT ON COLUMN t_sra_study.study IS 'スタディ';

COMMENT ON COLUMN t_sra_study.loaded IS 'ローデッド';

COMMENT ON COLUMN t_sra_study.spots IS 'スポット';

COMMENT ON COLUMN t_sra_study.bases IS 'ベース';

COMMENT ON COLUMN t_sra_study.md5sum IS 'md5値';

COMMENT ON COLUMN t_sra_study.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_sra_study.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_sra_study.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_sra_study.created_at IS '作成日時';

COMMENT ON COLUMN t_sra_study.updated_at IS '更新日時';

CREATE TABLE t_sra_submission
(
    accession  varchar(14) NOT NULL,
    submission varchar(14) NOT NULL,
    status     varchar(11) NOT NULL,
    updated    timestamp  ,
    published  timestamp  ,
    received   timestamp  ,
    type       varchar(10) NOT NULL,
    center     text       ,
    visibility text        NOT NULL,
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

COMMENT ON TABLE t_sra_submission IS 'SRAサブミッション';

COMMENT ON COLUMN t_sra_submission.accession IS 'アクセッション';

COMMENT ON COLUMN t_sra_submission.submission IS 'サブミッション';

COMMENT ON COLUMN t_sra_submission.status IS 'ステータス';

COMMENT ON COLUMN t_sra_submission.updated IS 'データ更新日';

COMMENT ON COLUMN t_sra_submission.published IS 'データ公開日';

COMMENT ON COLUMN t_sra_submission.received IS 'データ受信日';

COMMENT ON COLUMN t_sra_submission.type IS 'データ種別';

COMMENT ON COLUMN t_sra_submission.center IS 'センター';

COMMENT ON COLUMN t_sra_submission.visibility IS 'ビジビリティ';

COMMENT ON COLUMN t_sra_submission.alias IS 'エイリアス';

COMMENT ON COLUMN t_sra_submission.experiment IS 'エクスペリメント';

COMMENT ON COLUMN t_sra_submission.sample IS 'サンプル';

COMMENT ON COLUMN t_sra_submission.study IS 'スタディ';

COMMENT ON COLUMN t_sra_submission.loaded IS 'ローデッド';

COMMENT ON COLUMN t_sra_submission.spots IS 'スポット';

COMMENT ON COLUMN t_sra_submission.bases IS 'ベース';

COMMENT ON COLUMN t_sra_submission.md5sum IS 'md5値';

COMMENT ON COLUMN t_sra_submission.biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_sra_submission.bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_sra_submission.replacedby IS 'リプレイスバイ';

COMMENT ON COLUMN t_sra_submission.created_at IS '作成日時';

COMMENT ON COLUMN t_sra_submission.updated_at IS '更新日時';

CREATE TABLE t_suppressed_metadata
(
    accession  varchar(14) NOT NULL,
    type       text        NOT NULL,
    json       text        NOT NULL,
    created_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (accession)
);

COMMENT ON TABLE t_suppressed_metadata IS 'サプレッスドメタデータ';

COMMENT ON COLUMN t_suppressed_metadata.accession IS 'アクセッション';

COMMENT ON COLUMN t_suppressed_metadata.type IS 'データ種別';

COMMENT ON COLUMN t_suppressed_metadata.json IS 'Json';

COMMENT ON COLUMN t_suppressed_metadata.created_at IS '作成日時';

COMMENT ON COLUMN t_suppressed_metadata.updated_at IS '更新日時';


