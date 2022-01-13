export const LOCALE = Object.freeze({
  JA     : 'ja',
  EN     : 'en',
  DEFAULT: 'ja'
});

export const CONTENT_TYPE = Object.freeze({
  MULTI_PART_FORM: 'MULTI_PART_FORM_should_be_delete'
});

export const ENTRY_STATUS = Object.freeze({
  UNSUBMITTED: 'Unsubmitted',
  SUBMITTED  : 'Submitted',
});

export const ENTRY_VALIDATION_STATUS = Object.freeze({
  UNVALIDATED: 'Unvalidated',
  VALIDATED  : 'Validated',
});

export const ENTRY_COMMENT_VISIBILITY = Object.freeze({
  EVERYONE: 'Everyone',
  CURATOR : 'Curator',
});

export const JVAR_ENTRY_TYPE = Object.freeze({
  SNP: 'SNP',
  SV : 'SV'
});

export const ENTRY_FILE_TYPE = Object.freeze({
  VCF     : 'VCF',
  WORKBOOK: 'WORKBOOK',
});

export const ENTRY_FILE_ACCEPT_EXTENSION = Object.freeze({
  XLSX  : '.xlsx',
  VCF   : '.vcf',
  VCF_GZ: '.vcf.gz'
});

export const VALIDATION_STATUS = Object.freeze({
  UNVALIDATED: 'Unvalidated',
  VALIDATING : 'Validating',
  VALIDATED  : 'Validated'
});

export const ENTRY_REQUEST_TYPE = Object.freeze({
  PUBLISH: 'PUBLIC', // NOTE: 動詞で揃えるた。サーバーの定数とは不一致
  CANCEL : 'CANCEL',
  UPDATE : 'UPDATE',
});
