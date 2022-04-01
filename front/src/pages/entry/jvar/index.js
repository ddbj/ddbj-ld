import Head from 'next/head';
import { FormattedMessage, useIntl } from 'react-intl';
import { Card, Button } from 'reactstrap';
import { useCallback } from 'react';

import { getUserPageServerSideProps } from '../../../services/page';
import { useGetEntryListQuery, useLazyGetEntryListQuery } from '../../../services/entryApi';

import { useTitle } from '../../../hooks/page';
import { useModal } from '../../../hooks/ui';
import { useJvarHelpPageUrl } from '../../../hooks/help';

import Breadcrumb from '../../../components/Breadcrumb';
import Flaticon from '../../../components/icons/Flaticon';
import { Page, PageHeader, PageTitle } from '../../../components/parts/pages';
import Loading from '../../../components/Loading';
import ErrorAlert from '../../../components/ErrorAlert';

import CreateJvarEntryModal from '../../../components/modals/entry/jvar/CreateJvarEntryModal';
import JvarEntryTable from '../../../components/tables/entry/jvar/JvarEntryTable';

export default function JvarEntryList () {
  const intl = useIntl();

  const title = useTitle(
    intl.formatMessage({ id: 'entry.jvar' })
  );

  const jvarHelpPageUrl = useJvarHelpPageUrl();

  const [
    isOpenedCreateJvarEntryModal,
    toggleOpenedCreateJvarEntryModal
  ] = useModal();

  const {
    data: jvarEntryList,
    isLoading,
    isSuccess,
    error
  } = useGetEntryListQuery(null, {
    refetchOnMountOrArgChange: true
  });
  const [getEntryList] = useLazyGetEntryListQuery();

  const handleCreated = useCallback(() => {
    getEntryList(null);
  }, [getEntryList]);

  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <Page>
        <Breadcrumb breadcrumb={[
          { label: intl.formatMessage({ id: 'entry' }) },
          { label: intl.formatMessage({ id: 'entry.jvar' }) },
        ]} />
        <PageHeader>
          <PageTitle className="flex-grow-1 d-flex align-items-start">
            <FormattedMessage id="entry.jvar" />
            <a href={jvarHelpPageUrl} target="_blank" rel="noreferrer" className="ms-2 mt-2 d-inline-block fs-5">
              <Flaticon name="info" />
            </a>
          </PageTitle>
          <div>
            <Button color="primary" onClick={toggleOpenedCreateJvarEntryModal}>
              <FormattedMessage id="entry.jvar.create" />
            </Button>
            <CreateJvarEntryModal
              isOpen={isOpenedCreateJvarEntryModal}
              toggle={toggleOpenedCreateJvarEntryModal}
              onCreated={handleCreated} />
          </div>
        </PageHeader>
        <ErrorAlert error={error} />
        {isLoading && <Loading />}
        {isSuccess && (
          <Card>
            <JvarEntryTable jvarEntryList={jvarEntryList} />
          </Card>
        )}
      </Page>
    </>
  );
}

export const getServerSideProps = getUserPageServerSideProps();
