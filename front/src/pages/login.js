import Head from 'next/head';
import { useIntl } from 'react-intl';

import { getGusetPageServerSideProps } from '../services/page';

import { useTitle } from '../hooks/page';

import { Page } from '../components/parts/pages';
import LoginWithKeyCloak from '../components/features/auth/LoginWithKeyCloak';

export default function Login () {
  const intl = useIntl();

  const title = useTitle(
    intl.formatMessage({ id: 'login' })
  );

  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <Page>
        <LoginWithKeyCloak callbackUrl="/account" />
      </Page>
    </>
  );
}

export const getServerSideProps = getGusetPageServerSideProps();
