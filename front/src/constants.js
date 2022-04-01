export const CLIENT_URL = process.env.NEXT_PUBLIC_CLIENT_URL
export const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL
export const ELASTICSEARCH_BASE_URL = process.env.NEXT_PUBLIC_ELASTICSEARCH_BASE_URL
export const HELP_SITE_BASE_URL = process.env.NEXT_PUBLIC_HELP_SITE_BASE_URL
export const RECAPTCHA_SITE_KEY = process.env.NEXT_PUBLIC_RECAPTCHA_SITE_KEY

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

export const RESPONSE_STATUS = Object.freeze({
  CONTINUE                       : 100,
  SWITCHING_PROTOCOLS            : 101,
  PROCESSING                     : 102,
  OK                             : 200,
  CREATED                        : 201,
  ACCEPTED                       : 202,
  NON_AUTHORITATIVE_INFORMATION  : 203,
  NO_CONTENT                     : 204,
  RESET_CONTENT                  : 205,
  PARTIAL_CONTENT                : 206,
  MULTI_STATUS                   : 207,
  MULTIPLE_CHOICES               : 300,
  MOVED_PERMANENTLY              : 301,
  MOVED_TEMPORARILY              : 302,
  SEE_OTHER                      : 303,
  NOT_MODIFIED                   : 304,
  USE_PROXY                      : 305,
  TEMPORARY_REDIRECT             : 307,
  PERMANENT_REDIRECT             : 308,
  BAD_REQUEST                    : 400,
  UNAUTHORIZED                   : 401,
  PAYMENT_REQUIRED               : 402,
  FORBIDDEN                      : 403,
  NOT_FOUND                      : 404,
  METHOD_NOT_ALLOWED             : 405,
  NOT_ACCEPTABLE                 : 406,
  PROXY_AUTHENTICATION_REQUIRED  : 407,
  REQUEST_TIMEOUT                : 408,
  CONFLICT                       : 409,
  GONE                           : 410,
  LENGTH_REQUIRED                : 411,
  PRECONDITION_FAILED            : 412,
  REQUEST_TOO_LONG               : 413,
  REQUEST_URI_TOO_LONG           : 414,
  UNSUPPORTED_MEDIA_TYPE         : 415,
  REQUESTED_RANGE_NOT_SATISFIABLE: 416,
  EXPECTATION_FAILED             : 417,
  IM_A_TEAPOT                    : 418,
  INSUFFICIENT_SPACE_ON_RESOURCE : 419,
  METHOD_FAILURE                 : 420,
  MISDIRECTED_REQUEST            : 421,
  UNPROCESSABLE_ENTITY           : 422,
  LOCKED                         : 423,
  FAILED_DEPENDENCY              : 424,
  PRECONDITION_REQUIRED          : 428,
  TOO_MANY_REQUESTS              : 429,
  REQUEST_HEADER_FIELDS_TOO_LARGE: 431,
  UNAVAILABLE_FOR_LEGAL_REASONS  : 451,
  INTERNAL_SERVER_ERROR          : 500,
  NOT_IMPLEMENTED                : 501,
  BAD_GATEWAY                    : 502,
  SERVICE_UNAVAILABLE            : 503,
  GATEWAY_TIMEOUT                : 504,
  HTTP_VERSION_NOT_SUPPORTED     : 505,
  INSUFFICIENT_STORAGE           : 507,
  NETWORK_AUTHENTICATION_REQUIRED: 511,
});

export const PAGE_ROLE = Object.freeze({
  DEFAULT: 'DEFAULT',
  GUEST  : 'GUEST',
  USER   : 'USER',
  ADMIN  : 'ADMIN',
});
