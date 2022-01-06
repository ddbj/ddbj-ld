import { useEffect } from 'react';
import { useRouter } from 'next/router';
import Head from 'next/head';
import { useIntl } from 'react-intl';

import { useTitle } from '../hooks/page';
import {
  useIsAuthorized,
  useAuthrizeUrl
} from '../hooks/auth';

import Page from '../components/Page';
import Loading from '../components/Loading';

export default function Login () {
  const intl = useIntl();
  const router = useRouter();

  const isAuthorized = useIsAuthorized();
  const authorizeURL = useAuthrizeUrl();

  const title = useTitle(
    intl.formatMessage({ id: 'login' })
  );

  useEffect(() => {
    window.location.href = authorizeURL;
  }, [authorizeURL]);

  useEffect(() => {
    if(!isAuthorized) return;
    router.replace('/');
  }, [router, isAuthorized]);

  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <Page>
        <Loading />
      </Page>
    </>
  );
}
