import { FormattedMessage } from 'react-intl';

import {
  ENTRY_STATUS,
  ENTRY_VALIDATION_STATUS,
  JVAR_ENTRY_TYPE,
  VALIDATION_STATUS,
  ENTRY_FILE_TYPE,
  ENTRY_REQUEST_TYPE
} from '../../constants';

export function EntryStatusLabel ({ status }) {
  switch (status) {
  case ENTRY_STATUS.SUBMITTED:
    return <FormattedMessage id="entry.status.submitted"/>;
  case ENTRY_STATUS.UNSUBMITTED:
    return <FormattedMessage id="entry.status.unsubmitted"/>;
  default:
    return <FormattedMessage id="entry.status.unexpected" values={{ status }}/>;
  }
}

export function EntryValidationStatusLabel ({ validationStatus }) {
  switch (validationStatus) {
  case ENTRY_VALIDATION_STATUS.VALIDATED:
    return <FormattedMessage id="entry.validation_status.validated"/>;
  case ENTRY_VALIDATION_STATUS.UNVALIDATED:
    return <FormattedMessage id="entry.validation_status.unvalidated"/>;
  default:
    return <FormattedMessage id="entry.validation_status.unexpected" values={{ validationStatus }}/>;
  }
}

export function JvarEntryTypeLabel ({ type }) {
  switch (validationStatus) {
  case JVAR_ENTRY_TYPE.SV:
    return <FormattedMessage id="entry.jvar.type.sv"/>;
  case JVAR_ENTRY_TYPE.SNP:
    return <FormattedMessage id="entry.jvar.type.snp"/>;
  default:
    return <FormattedMessage id="entry.jvar.type.unexpected" values={{ type }}/>;
  }
}

export function ValidateStatusLabel ({ validationStatus }) {
  switch (validationStatus) {
  case VALIDATION_STATUS.UNVALIDATED:
    return <FormattedMessage id="entry.validation_status.unvalidated"/>;
  case VALIDATION_STATUS.VALIDATING:
    return <FormattedMessage id="entry.validation_status.validating"/>;
  case VALIDATION_STATUS.VALIDATED:
    return <FormattedMessage id="entry.validation_status.validated"/>;
  default:
    return <FormattedMessage id="entry.validation_status.unexpected" values={{ validationStatus }}/>;
  }
}

export function EntryFileTypeLabel ({ fileType }) {
  switch (fileType) {
  case ENTRY_FILE_TYPE.WORKBOOK:
    return <FormattedMessage id="entry.file.type.workbook"/>;
  case ENTRY_FILE_TYPE.VCF:
    return <FormattedMessage id="entry.file.type.vcf"/>;
  default:
    return <FormattedMessage id="entry.file.type.unexpected" values={{ fileType }}/>;
  }
}

export function EntryRequestTypeLabel ({ requestType }) {
  switch (requestType) {
  case ENTRY_REQUEST_TYPE.CANCEL:
    return <FormattedMessage id="entry.request.type.cancel"/>;
  case ENTRY_REQUEST_TYPE.UPDATE:
    return <FormattedMessage id="entry.request.type.update"/>;
  case ENTRY_REQUEST_TYPE.PUBLISH:
    return <FormattedMessage id="entry.request.type.publish"/>;
  default:
    return <FormattedMessage id="entry.request.type.unexpected" values={{ requestType }}/>;
  }
}

export function EntryActiveRequestLabel ({ activeRequest }) {

  if (activeRequest === 'None') {
    return <FormattedMessage id="none" />;
  }

  return activeRequest;
}
