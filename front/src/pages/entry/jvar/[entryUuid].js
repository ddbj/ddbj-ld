import Head from 'next/head';
import { useRouter } from 'next/router';
import { useIntl } from 'react-intl';

import { useGetEntryQuery } from '@/services/entryApi';
import { useTitle } from '@/hooks/page';
import ErrorAlert from '@/components/parts/ErrorAlert';
import Breadcrumb from '@/components/parts/Breadcrumb';
import Loading from '@/components/parts/Loading';
import { PageTitle, PageHeader } from '@/components/parts/Page';
import { NavigatedPageContainer, NavigatedPageSection } from '@/components/parts/Page/navigatedPage';

import ViewJvarEntrySummary from '@/components/features/entry/jvar/ViewJvarEntrySummary';
import ManageEntryFiles from '@/components/features/entry/ManageEntryFiles';
import ValidateEntry from '@/components/features/entry/ValidateEntry';
import SubmitEntry from '@/components/features/entry/SubmitEntry';
import RequestEntry from '@/components/features/entry/RequestEntry';
import ManageEntryComments from '@/components/features/entry/ManageEntryComments';
import DeleteEntry from '@/components/features/entry/DeleteEntry';

export default function JvarPage () {
  const intl = useIntl();
  const { query: { entryUuid } } = useRouter();
  const title = useTitle(
    intl.formatMessage({ id: 'entry.jvar' }),
  );
  const {
    data: jvarEntry,
    error,
    isLoading,
    isSuccess
  } = useGetEntryQuery({ entryUuid }, {
    skip                     : !entryUuid,
    refetchOnMountOrArgChange: true
  });

  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <Breadcrumb breadcrumb={[{
        label: intl.formatMessage({ id: 'entry' })
      }, {
        label: intl.formatMessage({ id: 'entry.jvar' }),
        href : '/entry/jvar'
      }, {
        label: jvarEntry?.label || entryUuid
      }]} />
      <PageHeader>
        <PageTitle className="flex-grow-1">
          {jvarEntry?.label || entryUuid}
        </PageTitle>
      </PageHeader>
      <ErrorAlert error={error} />
      {isLoading && <Loading />}
      {isSuccess && (
        <NavigatedPageContainer>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.jvar.summary' })} id="summary">
            <ViewJvarEntrySummary entryUuid={entryUuid} />
          </NavigatedPageSection>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.file' })} id="file">
            <ManageEntryFiles entryUuid={entryUuid} />
          </NavigatedPageSection>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.validation' })} id="validation">
            <ValidateEntry entryUuid={entryUuid} />
          </NavigatedPageSection>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.submission' })} id="submission">
            <SubmitEntry entryUuid={entryUuid} />
          </NavigatedPageSection>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.request' })} id="request">
            <RequestEntry entryUuid={entryUuid} />
          </NavigatedPageSection>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.comment' })} id="comment">
            <ManageEntryComments entryUuid={entryUuid} />
          </NavigatedPageSection>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.deletion' })} id="deletion">
            <DeleteEntry entryUuid={entryUuid} />
          </NavigatedPageSection>
        </NavigatedPageContainer>
      )}
    </>
  );
}
