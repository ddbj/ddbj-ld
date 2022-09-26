import { useCallback, useMemo } from 'react';
import { FormattedMessage } from 'react-intl';
import {
  Card, CardBody,
  Button,
} from 'reactstrap';

import {
  useGetEntryQuery,
  useLazyGetEntryQuery
} from '../../../services/entryApi';

import { useModal } from '../../../hooks/ui';

import Loading from '../../parts/Loading';
import ErrorAlert from '@/components/parts/ErrorAlert';

import CreateEntryRequestModal from '../../parts/modals/entry/CreateEntryRequestModal';
import EntryRequestTable from '../../parts/tables/entry/EntryRequestTable';

export default function EntryRequestWidget ({ entryUuid }) {
  const [
    isCreateEntryRequestModalOpen,
    toggleCreateEntryRequestModal
  ] = useModal(false);

  const {
    data: entry,
    error: getError,
    isSuccess, isLoading
  } = useGetEntryQuery({ entryUuid });
  const [getEntry] = useLazyGetEntryQuery();

  const isRequestable = useMemo(() => {
    return entry?.menu?.requestMenu?.request;
  }, [entry?.menu?.requestMenu?.request]);

  const refresh = useCallback(() => getEntry({ entryUuid }), [entryUuid, getEntry]);

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
              onCreated={refresh} />
          </CardBody>
        )}
        {isLoading && <Loading />}
        {isSuccess && (
          <EntryRequestTable
            entryUuid={entryUuid}
            entryRequestList={entry.requests}
            onUpdated={refresh}
            onDeleted={refresh}
            onApplied={refresh}
            onRejected={refresh} />

        )}
      </Card>
    </>
  );
}
