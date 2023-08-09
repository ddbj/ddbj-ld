import { useMemo } from 'react';
import { FormattedMessage } from 'react-intl';
import { Card, CardBody, Button } from 'reactstrap';

import { useGetEntryQuery } from '@/services/entryApi';
import { useModal } from '@/hooks/ui';
import Loading from '@/components/parts/Loading';
import ErrorAlert from '@/components/parts/ErrorAlert';
import CreateEntryRequestModal from '@/components/parts/entry/CreateEntryRequestModal';
import EntryRequestTable from '@/components/parts/entry/EntryRequestTable';

export default function RequestEntry ({ entryUuid }) {
  const [
    isCreateEntryRequestModalOpen,
    toggleCreateEntryRequestModal
  ] = useModal(false);

  const {
    data: entry,
    error: getError,
    isSuccess, isLoading,
    refetch
  } = useGetEntryQuery({ entryUuid });

  const isRequestable = useMemo(() => {
    return entry?.menu?.requestMenu?.request;
  }, [entry?.menu?.requestMenu?.request]);

  return (
    <>
      <Card>
        <ErrorAlert error={getError} />
        {isRequestable && (
          <CardBody className="d-flex justify-content-end">
            <Button color="primary" onClick={toggleCreateEntryRequestModal}>
              <FormattedMessage id="entry.request.create" />
            </Button>
            <CreateEntryRequestModal
              entryUuid={entryUuid}
              isOpen={isCreateEntryRequestModalOpen}
              toggle={toggleCreateEntryRequestModal}
              onCreated={refetch} />
          </CardBody>
        )}
        {isLoading && <Loading />}
        {isSuccess && (
          <EntryRequestTable
            entryUuid={entryUuid}
            entryRequestList={entry.requests}
            onUpdated={refetch}
            onDeleted={refetch}
            onApplied={refetch}
            onRejected={refetch} />
        )}
      </Card>
    </>
  );
}
