import { useMemo } from 'react';
import { useIntl } from 'react-intl';

import { Alert } from 'reactstrap';

export default function ErrorAlert ({ error }) {
  const intl = useIntl();

  const message = useMemo(() => {
    switch(error?.status) {
    case 400:
      return `${intl.formatMessage({ id: 'error.400' })}: ${intl.formatMessage({ id: 'error.400.message' })}`;
    case 401:
      return `${intl.formatMessage({ id: 'error.401' })}: ${intl.formatMessage({ id: 'error.401.message' })}`;
    case 403:
      return `${intl.formatMessage({ id: 'error.403' })}: ${intl.formatMessage({ id: 'error.403.message' })}`;
    case 404:
      return `${intl.formatMessage({ id: 'error.404' })}: ${intl.formatMessage({ id: 'error.404.message' })}`;
    case 500:
      return `${intl.formatMessage({ id: 'error.500' })}: ${intl.formatMessage({ id: 'error.500.message' })}`;
    default:
      return intl.formatMessage({ id: 'error.unexpected' });
    }
  }, [error, intl]);

  if (!error) return null;

  return <Alert color="danger">{message}</Alert>;
}
