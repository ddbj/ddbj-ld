import { useMemo } from 'react';
import { useIntl } from 'react-intl';

export function useErrorContents(statusCode) {
  const intl = useIntl();

  return useMemo(() => {
    switch(statusCode) {
    case 400:
      return {
        title  : intl.formatMessage({ id: 'error.400' }),
        message: intl.formatMessage({ id: 'error.400.message' }),
      };
    case 401:
      return {
        title  : intl.formatMessage({ id: 'error.401' }),
        message: intl.formatMessage({ id: 'error.401.message' }),
      };
    case 403:
      return {
        title  : intl.formatMessage({ id: 'error.403' }),
        message: intl.formatMessage({ id: 'error.403.message' }),
      };
    case 404:
      return {
        title  : intl.formatMessage({ id: 'error.404' }),
        message: intl.formatMessage({ id: 'error.404.message' }),
      };
    case 500:
      return {
        title  : intl.formatMessage({ id: 'error.500' }),
        message: intl.formatMessage({ id: 'error.500.message' }),
      };
    default:
      return {
        title  : intl.formatMessage({ id: 'error.unexpected' }),
        message: '',
      };
    }
  }, [intl, statusCode]);
}
