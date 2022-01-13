import { useMemo } from 'react';
import { useRouter } from 'next/router';
import { IntlProvider } from 'react-intl';

import allMessages from '../intl';

export function NextIntlProvider (props) {
  const { locale } = useRouter();
  const messages = useMemo(() => allMessages[locale], [locale]);
  return <IntlProvider locale={locale} messages={messages} {...props} />;
}
