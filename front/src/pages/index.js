import Head from 'next/head';
import { useIntl } from 'react-intl';
import { useTitle } from '@/hooks/page';
import Page from '@/components/parts/Page';
import ViewWelcome from '@/components/features/ViewWelcome';

export default function Home () {
  const intl = useIntl();
  const title = useTitle( intl.formatMessage({ id: 'home' }) );
  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <Page>
        <ViewWelcome />
      </Page>
    </>
  );
}
