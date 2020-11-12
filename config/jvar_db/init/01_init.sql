
        
CREATE TABLE h_entry
(
  uuid              uuid      NOT NULL,
  label             varchar   NOT NULL,
  revision          integer   NOT NULL DEFAULT 1,
  title             varchar  ,
  description       text     ,
  status            varchar   NOT NULL,
  validation_status varchar   NOT NULL,
  metadata_json     text     ,
  aggregate_json    text     ,
  created_at        timestamp NOT NULL DEFAULT current_timestamp,
  updated_at        timestamp NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (uuid, label, revision)
);

COMMENT ON TABLE h_entry IS 'エントリー履歴';

COMMENT ON COLUMN h_entry.uuid IS 'UUID';

COMMENT ON COLUMN h_entry.label IS 'ラベル';

COMMENT ON COLUMN h_entry.revision IS 'リビジョン';

COMMENT ON COLUMN h_entry.title IS 'タイトル';

COMMENT ON COLUMN h_entry.description IS '要約';

COMMENT ON COLUMN h_entry.status IS 'ステータス';

COMMENT ON COLUMN h_entry.validation_status IS 'バリデーションステータス';

COMMENT ON COLUMN h_entry.metadata_json IS 'メタデータJSON';

COMMENT ON COLUMN h_entry.aggregate_json IS '集計データJSON';

COMMENT ON COLUMN h_entry.created_at IS '作成日時';

COMMENT ON COLUMN h_entry.updated_at IS '更新日時';

CREATE TABLE t_account
(
  uuid          uuid    NOT NULL,
  uid           varchar NOT NULL UNIQUE,
  refresh_token uuid   ,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_account IS 'アカウント';

COMMENT ON COLUMN t_account.uuid IS 'UUID';

COMMENT ON COLUMN t_account.uid IS 'DDBJアカウントUID';

COMMENT ON COLUMN t_account.refresh_token IS 'リフレッシュトークン';

CREATE TABLE t_assay
(
  uuid            uuid    NOT NULL,
  id              varchar NOT NULL UNIQUE,
  entry_uuid      uuid    NOT NULL,
  experiment_uuid uuid   ,
  sampleset_uuid  uuid    NOT NULL,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_assay IS 'アッセイ';

COMMENT ON COLUMN t_assay.uuid IS 'UUID';

COMMENT ON COLUMN t_assay.id IS 'ID';

COMMENT ON COLUMN t_assay.entry_uuid IS 'エントリーUUID';

COMMENT ON COLUMN t_assay.experiment_uuid IS '実験UUID';

COMMENT ON COLUMN t_assay.sampleset_uuid IS 'サンプルセットUUID';

CREATE TABLE t_bioproject
(
  uuid      uuid    NOT NULL,
  accession varchar UNIQUE,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_bioproject IS 'バイオプロジェクト';

COMMENT ON COLUMN t_bioproject.uuid IS 'UUID';

COMMENT ON COLUMN t_bioproject.accession IS 'アクセッション';

CREATE TABLE t_biosample
(
  uuid      uuid    NOT NULL,
  accession varchar,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_biosample IS 'バイオサンプル';

COMMENT ON COLUMN t_biosample.uuid IS 'UUID';

COMMENT ON COLUMN t_biosample.accession IS 'アクセッション';

CREATE TABLE t_comment
(
  uuid         uuid      NOT NULL,
  entry_uuid   uuid      NOT NULL,
  account_uuid uuid      NOT NULL,
  comment      text      NOT NULL,
  created_at   timestamp NOT NULL DEFAULT current_timestamp,
  updated_at   timestamp NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_comment IS 'コメント';

COMMENT ON COLUMN t_comment.uuid IS 'UUID';

COMMENT ON COLUMN t_comment.entry_uuid IS 'エントリーUUID';

COMMENT ON COLUMN t_comment.account_uuid IS 'アカウントUUID';

COMMENT ON COLUMN t_comment.comment IS 'コメント';

COMMENT ON COLUMN t_comment.created_at IS '作成日時';

COMMENT ON COLUMN t_comment.updated_at IS '更新日付';

CREATE TABLE t_entry
(
  uuid              uuid      NOT NULL,
  label             varchar   NOT NULL UNIQUE,
  title             varchar  ,
  description       text     ,
  status            varchar   NOT NULL,
  validation_status varchar   NOT NULL,
  metadata_json     text     ,
  aggregate_json    text     ,
  created_at        timestamp NOT NULL DEFAULT current_timestamp,
  updated_at        timestamp NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_entry IS 'エントリー';

COMMENT ON COLUMN t_entry.uuid IS 'UUID';

COMMENT ON COLUMN t_entry.label IS 'ラベル';

COMMENT ON COLUMN t_entry.title IS 'タイトル';

COMMENT ON COLUMN t_entry.description IS 'デスクリプション';

COMMENT ON COLUMN t_entry.status IS 'ステータス';

COMMENT ON COLUMN t_entry.validation_status IS 'バリデーションステータス';

COMMENT ON COLUMN t_entry.metadata_json IS 'メタデータJSON';

COMMENT ON COLUMN t_entry.aggregate_json IS '集計データJSON';

COMMENT ON COLUMN t_entry.created_at IS '作成日時';

COMMENT ON COLUMN t_entry.updated_at IS '更新日時';

CREATE TABLE t_entry_role
(
  account_uuid uuid NOT NULL,
  entry_uuid   uuid NOT NULL,
  PRIMARY KEY (account_uuid, entry_uuid)
);

COMMENT ON TABLE t_entry_role IS 'エントリー権限';

COMMENT ON COLUMN t_entry_role.account_uuid IS 'アカウントUUID';

COMMENT ON COLUMN t_entry_role.entry_uuid IS 'エントリーUUID';

CREATE TABLE t_experiment
(
  uuid       uuid    NOT NULL,
  id         varchar NOT NULL UNIQUE,
  entry_uuid uuid    NOT NULL,
  study_uuid uuid   ,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_experiment IS '実験';

COMMENT ON COLUMN t_experiment.uuid IS 'UUID';

COMMENT ON COLUMN t_experiment.id IS 'ID';

COMMENT ON COLUMN t_experiment.entry_uuid IS 'エントリーUUID';

COMMENT ON COLUMN t_experiment.study_uuid IS '研究UUID';

CREATE TABLE t_file
(
  uuid       uuid    NOT NULL,
  entry_uuid uuid    NOT NULL,
  type       varchar NOT NULL,
  name       varchar NOT NULL,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_file IS 'ファイル';

COMMENT ON COLUMN t_file.uuid IS 'UUID';

COMMENT ON COLUMN t_file.entry_uuid IS 'エントリーUUID';

COMMENT ON COLUMN t_file.type IS 'ファイル種別';

COMMENT ON COLUMN t_file.name IS 'ファイル名';

CREATE TABLE t_request
(
  uuid         uuid      NOT NULL,
  entry_uuid   uuid      NOT NULL,
  account_uuid uuid      NOT NULL,
  type         varchar   NOT NULL,
  comment      text     ,
  closed       boolean   NOT NULL DEFAULT false,
  created_at   timestamp NOT NULL DEFAULT current_timestamp,
  updated_at   timestamp NOT NULL DEFAULT current_timestamp,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_request IS '申請';

COMMENT ON COLUMN t_request.uuid IS 'UUID';

COMMENT ON COLUMN t_request.entry_uuid IS 'エントリーUUID';

COMMENT ON COLUMN t_request.account_uuid IS 'アカウントUUID';

COMMENT ON COLUMN t_request.type IS '申請タイプ';

COMMENT ON COLUMN t_request.comment IS 'コメント';

COMMENT ON COLUMN t_request.closed IS '完了フラグ';

COMMENT ON COLUMN t_request.created_at IS '作成日時';

COMMENT ON COLUMN t_request.updated_at IS '更新日時';

CREATE TABLE t_sample
(
  uuid            uuid    NOT NULL,
  id              varchar NOT NULL UNIQUE,
  entry_uuid      uuid    NOT NULL,
  experiment_uuid uuid   ,
  biosample_uuid  uuid   ,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_sample IS 'サンプル';

COMMENT ON COLUMN t_sample.uuid IS 'UUID';

COMMENT ON COLUMN t_sample.id IS 'ID';

COMMENT ON COLUMN t_sample.entry_uuid IS 'エントリーUUID';

COMMENT ON COLUMN t_sample.experiment_uuid IS '実験UUID';

COMMENT ON COLUMN t_sample.biosample_uuid IS 'バイオサンプルUUID';

CREATE TABLE t_sampleset
(
  uuid           uuid    NOT NULL,
  id             varchar NOT NULL UNIQUE,
  entry_uuid     uuid   ,
  biosample_uuid uuid   ,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_sampleset IS 'サンプルセット';

COMMENT ON COLUMN t_sampleset.uuid IS 'UUID';

COMMENT ON COLUMN t_sampleset.id IS 'ID';

COMMENT ON COLUMN t_sampleset.entry_uuid IS 'エントリーUUID';

COMMENT ON COLUMN t_sampleset.biosample_uuid IS 'バイオサンプルUUID';

CREATE TABLE t_study
(
  uuid            uuid    NOT NULL,
  id              varchar NOT NULL UNIQUE,
  entry_uuid      uuid    NOT NULL,
  bioproject_uuid uuid   ,
  pubmed_id       varchar,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_study IS '研究';

COMMENT ON COLUMN t_study.uuid IS 'UUID';

COMMENT ON COLUMN t_study.id IS 'ID';

COMMENT ON COLUMN t_study.entry_uuid IS 'エントリーUUID';

COMMENT ON COLUMN t_study.bioproject_uuid IS 'バイオプロジェクトUUID';

COMMENT ON COLUMN t_study.pubmed_id IS 'パムメドID';

CREATE TABLE t_upload
(
  uuid      uuid    NOT NULL,
  file_uuid uuid    NOT NULL,
  ended     boolean NOT NULL DEFAULT false,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_upload IS 'アップロード';

COMMENT ON COLUMN t_upload.uuid IS 'アップロードUUID';

COMMENT ON COLUMN t_upload.file_uuid IS 'ファイルUUID';

COMMENT ON COLUMN t_upload.ended IS '終了フラグ';

CREATE TABLE t_user
(
  uuid         uuid    NOT NULL,
  account_uuid uuid    NOT NULL,
  admin        boolean NOT NULL DEFAULT false,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_user IS 'ユーザー';

COMMENT ON COLUMN t_user.uuid IS 'UUID';

COMMENT ON COLUMN t_user.account_uuid IS 'アカウントUUID';

COMMENT ON COLUMN t_user.admin IS '管理者権限';

CREATE TABLE t_validation
(
  uuid        uuid    NOT NULL,
  file_uuid   uuid    NOT NULL,
  status      varchar NOT NULL,
  description text   ,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_validation IS 'バリデーション';

COMMENT ON COLUMN t_validation.uuid IS 'UUID';

COMMENT ON COLUMN t_validation.file_uuid IS 'ファイルUUID';

COMMENT ON COLUMN t_validation.status IS 'ステータス';

COMMENT ON COLUMN t_validation.description IS 'デスクリプション';

CREATE TABLE t_variant_call
(
  uuid                uuid    NOT NULL,
  id                  varchar NOT NULL UNIQUE,
  entry_uuid          uuid    NOT NULL,
  variant_region_uuid uuid   ,
  experiment_uuid     uuid   ,
  sampleset_uuid      uuid   ,
  sample_uuid         uuid   ,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_variant_call IS 'バリアントコール';

COMMENT ON COLUMN t_variant_call.uuid IS 'UUID';

COMMENT ON COLUMN t_variant_call.id IS 'ID';

COMMENT ON COLUMN t_variant_call.entry_uuid IS 'エントリーUUID';

COMMENT ON COLUMN t_variant_call.variant_region_uuid IS 'バリアントリージョンUUID';

COMMENT ON COLUMN t_variant_call.experiment_uuid IS '実験UUID';

COMMENT ON COLUMN t_variant_call.sampleset_uuid IS 'サンプルセットUUID';

COMMENT ON COLUMN t_variant_call.sample_uuid IS 'サンプルUUID';

CREATE TABLE t_variant_region
(
  uuid       uuid    NOT NULL,
  id         varchar NOT NULL UNIQUE,
  entry_uuid uuid    NOT NULL,
  PRIMARY KEY (uuid)
);

COMMENT ON TABLE t_variant_region IS 'バリアントリージョン';

COMMENT ON COLUMN t_variant_region.uuid IS 'UUID';

COMMENT ON COLUMN t_variant_region.id IS 'ID';

COMMENT ON COLUMN t_variant_region.entry_uuid IS 'エントリーUUID';

ALTER TABLE t_user
  ADD CONSTRAINT FK_t_account_TO_t_user
    FOREIGN KEY (account_uuid)
    REFERENCES t_account (uuid);

ALTER TABLE t_entry_role
  ADD CONSTRAINT FK_t_account_TO_t_entry_role
    FOREIGN KEY (account_uuid)
    REFERENCES t_account (uuid);

ALTER TABLE t_entry_role
  ADD CONSTRAINT FK_t_entry_TO_t_entry_role
    FOREIGN KEY (entry_uuid)
    REFERENCES t_entry (uuid);

ALTER TABLE h_entry
  ADD CONSTRAINT FK_t_entry_TO_h_entry
    FOREIGN KEY (uuid)
    REFERENCES t_entry (uuid);

ALTER TABLE t_file
  ADD CONSTRAINT FK_t_entry_TO_t_file
    FOREIGN KEY (entry_uuid)
    REFERENCES t_entry (uuid);

ALTER TABLE t_upload
  ADD CONSTRAINT FK_t_file_TO_t_upload
    FOREIGN KEY (file_uuid)
    REFERENCES t_file (uuid);

ALTER TABLE t_comment
  ADD CONSTRAINT FK_t_entry_TO_t_comment
    FOREIGN KEY (entry_uuid)
    REFERENCES t_entry (uuid);

ALTER TABLE t_request
  ADD CONSTRAINT FK_t_entry_TO_t_request
    FOREIGN KEY (entry_uuid)
    REFERENCES t_entry (uuid);

ALTER TABLE t_study
  ADD CONSTRAINT FK_t_entry_TO_t_study
    FOREIGN KEY (entry_uuid)
    REFERENCES t_entry (uuid);

ALTER TABLE t_experiment
  ADD CONSTRAINT FK_t_entry_TO_t_experiment
    FOREIGN KEY (entry_uuid)
    REFERENCES t_entry (uuid);

ALTER TABLE t_assay
  ADD CONSTRAINT FK_t_entry_TO_t_assay
    FOREIGN KEY (entry_uuid)
    REFERENCES t_entry (uuid);

ALTER TABLE t_sampleset
  ADD CONSTRAINT FK_t_entry_TO_t_sampleset
    FOREIGN KEY (entry_uuid)
    REFERENCES t_entry (uuid);

ALTER TABLE t_sample
  ADD CONSTRAINT FK_t_entry_TO_t_sample
    FOREIGN KEY (entry_uuid)
    REFERENCES t_entry (uuid);

ALTER TABLE t_request
  ADD CONSTRAINT FK_t_account_TO_t_request
    FOREIGN KEY (account_uuid)
    REFERENCES t_account (uuid);

ALTER TABLE t_comment
  ADD CONSTRAINT FK_t_account_TO_t_comment
    FOREIGN KEY (account_uuid)
    REFERENCES t_account (uuid);

ALTER TABLE t_experiment
  ADD CONSTRAINT FK_t_study_TO_t_experiment
    FOREIGN KEY (study_uuid)
    REFERENCES t_study (uuid);

ALTER TABLE t_sample
  ADD CONSTRAINT FK_t_experiment_TO_t_sample
    FOREIGN KEY (experiment_uuid)
    REFERENCES t_experiment (uuid);

ALTER TABLE t_study
  ADD CONSTRAINT FK_t_bioproject_TO_t_study
    FOREIGN KEY (bioproject_uuid)
    REFERENCES t_bioproject (uuid);

ALTER TABLE t_sample
  ADD CONSTRAINT FK_t_biosample_TO_t_sample
    FOREIGN KEY (biosample_uuid)
    REFERENCES t_biosample (uuid);

ALTER TABLE t_assay
  ADD CONSTRAINT FK_t_experiment_TO_t_assay
    FOREIGN KEY (experiment_uuid)
    REFERENCES t_experiment (uuid);

ALTER TABLE t_assay
  ADD CONSTRAINT FK_t_sampleset_TO_t_assay
    FOREIGN KEY (sampleset_uuid)
    REFERENCES t_sampleset (uuid);

ALTER TABLE t_variant_call
  ADD CONSTRAINT FK_t_entry_TO_t_variant_call
    FOREIGN KEY (entry_uuid)
    REFERENCES t_entry (uuid);

ALTER TABLE t_variant_region
  ADD CONSTRAINT FK_t_entry_TO_t_variant_region
    FOREIGN KEY (entry_uuid)
    REFERENCES t_entry (uuid);

ALTER TABLE t_variant_call
  ADD CONSTRAINT FK_t_variant_region_TO_t_variant_call
    FOREIGN KEY (variant_region_uuid)
    REFERENCES t_variant_region (uuid);

ALTER TABLE t_variant_call
  ADD CONSTRAINT FK_t_experiment_TO_t_variant_call
    FOREIGN KEY (experiment_uuid)
    REFERENCES t_experiment (uuid);

ALTER TABLE t_variant_call
  ADD CONSTRAINT FK_t_sample_TO_t_variant_call
    FOREIGN KEY (sample_uuid)
    REFERENCES t_sample (uuid);

ALTER TABLE t_variant_call
  ADD CONSTRAINT FK_t_sampleset_TO_t_variant_call
    FOREIGN KEY (sampleset_uuid)
    REFERENCES t_sampleset (uuid);

ALTER TABLE t_sampleset
  ADD CONSTRAINT FK_t_biosample_TO_t_sampleset
    FOREIGN KEY (biosample_uuid)
    REFERENCES t_biosample (uuid);

ALTER TABLE t_validation
  ADD CONSTRAINT FK_t_file_TO_t_validation
    FOREIGN KEY (file_uuid)
    REFERENCES t_file (uuid);

      