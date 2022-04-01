import { useRouter } from 'next/router';
import { useMemo } from 'react';

import allMessages from '../../../intl';

export function useNextIntlProvider () {
  const { locale } = useRouter();
  const messages = useMemo(() => allMessages[locale], [locale]);

  return { locale, messages };
}
