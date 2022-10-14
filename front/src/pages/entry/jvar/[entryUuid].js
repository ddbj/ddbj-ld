import Head from 'next/head';
import { useRouter } from 'next/router';
import { useIntl } from 'react-intl';

import { useGetEntryQuery } from '@/services/entryApi';
import { useTitle } from '@/hooks/page';
import ErrorAlert from '@/components/parts/ErrorAlert';
import Breadcrumb from '@/components/parts/Breadcrumb';
import Loading from '@/components/parts/Loading';
import {
  PageTitle,
  PageHeader,
  NavigatedPageSection,
  NavigatedPageContainer
} from '@/components/parts/Page';

import JvarEntrySummaryWidget from '@/components/widgets/entry/jvar/JvarEntrySummaryWidget';
import EntryFileWidget from '@/components/widgets/entry/EntryFileWidget';
import EntryValidationWidget from '@/components/widgets/entry/EntryValidationWidget';
import EntrySubmissionWidget from '@/components/widgets/entry/EntrySubmissionWidget';
import EntryRequestWidget from '@/components/widgets/entry/EntryRequestWidget';
import EntryCommentWidget from '@/components/widgets/entry/EntryCommentWidget';
import EntryDeletionWidget from '@/components/widgets/entry/EntryDeletionWidget';

export default function JvarEntrySingle () {
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
            <JvarEntrySummaryWidget entryUuid={entryUuid} />
          </NavigatedPageSection>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.file' })} id="file">
            <EntryFileWidget entryUuid={entryUuid} />
          </NavigatedPageSection>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.validation' })} id="validation">
            <EntryValidationWidget entryUuid={entryUuid} />
          </NavigatedPageSection>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.submission' })} id="submission">
            <EntrySubmissionWidget entryUuid={entryUuid} />
          </NavigatedPageSection>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.request' })} id="request">
            <EntryRequestWidget entryUuid={entryUuid} />
          </NavigatedPageSection>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.comment' })} id="comment">
            <EntryCommentWidget entryUuid={entryUuid} />
          </NavigatedPageSection>
          <NavigatedPageSection label={intl.formatMessage({ id: 'entry.deletion' })} id="deletion">
            <EntryDeletionWidget entryUuid={entryUuid} />
          </NavigatedPageSection>
        </NavigatedPageContainer>
      )}
    </>
  );
}
