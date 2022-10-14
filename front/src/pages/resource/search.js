import Head from 'next/head';
import { useIntl } from 'react-intl';
import { useTitle } from '@/hooks/page';
import Page from '@/components/parts/Page';
import SearchResource from '@/components/features/SerachResource';

export default function ResourceSearchPage () {
  const intl = useIntl();
  const title = useTitle( intl.formatMessage({ id: 'home' }) );
  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <Page className="px-2 py-4">
        <SearchResource />
      </Page>
    </>
  );
}
