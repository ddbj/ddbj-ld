export const CLIENT_URL = process.env.NEXT_PUBLIC_CLIENT_URL
export const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL
export const ELASTICSEARCH_URL = process.env.NEXT_PUBLIC_ELASTICSEARCH_URL
export const IDP_URL = process.env.NEXT_PUBLIC_IDP_URL
export const IDP_CLIENT_ID = process.env.NEXT_PUBLIC_IDP_CLIENT_ID
export const IDP_SCOPE = process.env.NEXT_PUBLIC_IDP_SCOPE
export const RE_CAPTCHA_SITE_KEY = process.env.NEXT_PUBLIC_RE_CAPTCHA_SITE_KEY
export const HELP_SITE_URL = process.env.NEXT_PUBLIC_HELP_SITE_URL

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
