import { IntlProvider } from 'react-intl';
import { useNextIntlProvider } from './hooks';

export function NextIntlProvider (props) {
  const { locale, messages } = useNextIntlProvider();

  return <IntlProvider locale={locale} messages={messages} {...props} />;
}
