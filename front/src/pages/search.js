import Head from 'next/head';
import { useIntl } from 'react-intl';
import { useTitle } from '@/hooks/page';
import Page from '@/components/parts/Page';
import Search from '@/components/features/Search';

export default function ResourceSearchPage () {
  const intl = useIntl();
  const title = useTitle( intl.formatMessage({ id: 'home' }) );
  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <Page>
        <Search />
      </Page>
    </>
  );
}
