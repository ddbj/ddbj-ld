import { useMemo } from 'react';
import { useIntl } from 'react-intl';

const PAGE_JOINNER = ' > ';

export function useTitle (title) {
  const intl = useIntl();

  return useMemo(() => {
    const pageTitle = Array.isArray(title) ? (
      title.join(PAGE_JOINNER)
    ) : (
      title
    );
    return intl.formatMessage({ id: 'site_title' }, { pageTitle });
  }, [title, intl]);
}
