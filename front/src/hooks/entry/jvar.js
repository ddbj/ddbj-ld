import { useMemo } from 'react';
import { useIntl } from 'react-intl';
import * as Yup from 'yup';

import {
  JVAR_ENTRY_TYPE
} from '../../constants';

export function useCreateJvarEntryValidationSchema () {
  const intl = useIntl();

  return useMemo(() => Yup.object().shape({
    type: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' }))
      .oneOf(Object.values(JVAR_ENTRY_TYPE), intl.formatMessage({ id: 'form.error.invalid_value' }))
      .default('')
  }), [intl]);
}
