import Head from 'next/head';
import { useEffect } from 'react';
import { useRouter } from 'next/router';
import { useIntl } from 'react-intl';

import { useTitle } from '../hooks/page';
import { useIsAuthorized } from '../hooks/auth';

import Page from '../components/Page';
import Loading from '../components/Loading';

export default function Home () {
  const intl = useIntl();
  const isAurhorized = useIsAuthorized();
  const router = useRouter();

  const title = useTitle(
    intl.formatMessage({ id: 'home' })
  );

  useEffect(() => {
    if (!isAurhorized) {
      router.replace('/resource/search');
      return;
    }

    router.replace('/account');
  }, [isAurhorized, router]);

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
