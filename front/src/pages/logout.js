import Head from 'next/head';
import { useIntl } from 'react-intl';

import { useTitle } from '../hooks/page';

import { Page } from '../components/parts/pages';

import DeleteSession from '../components/features/auth/DeleteSession';

export default function Logout () {
  const intl = useIntl();

  const title = useTitle(
    intl.formatMessage({ id: 'logout' })
  );

  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <Page>
        <DeleteSession callbackUrl="/resource/search" />
      </Page>
    </>
  );
}
