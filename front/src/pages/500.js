import Head from 'next/head';
import { useIntl, FormattedMessage } from 'react-intl';

import { useTitle } from '../hooks/page';

import Page, { PageTitle } from '../components/Page';

export default function BadRequest () {
  const intl = useIntl();
  const title = useTitle(intl.formatMessage({ id: 'error.500' }));

  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <Page>
        <PageTitle>
          <FormattedMessage id="error.500"/>
        </PageTitle>
        <p><FormattedMessage id="error.500.message"/></p>
      </Page>
    </>
  );
}
