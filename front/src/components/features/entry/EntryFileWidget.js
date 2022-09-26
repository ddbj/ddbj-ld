import { useCallback } from 'react';
import { FormattedMessage } from 'react-intl';
import {
  Card,
  CardBody,
  Button
} from 'reactstrap';

import {
  useGetEntryQuery,
  useLazyGetEntryQuery
} from '../../../services/entryApi';

import { useModal } from '../../../hooks/ui';

import ErrorAlert from '@/components/parts/ErrorAlert';

import UploadEntryFileModal from '../../parts/modals/entry/UploadEntryFileModal';
import EntryFileTable from '../../parts/tables/entry/EntryFileTable';

export default function EntryFileWidget ({ entryUuid }) {
  const [
    isUploadEntryFileModalOpen,
    toggleUploadEntryFileModal
  ] = useModal(false);

  const {
    data: jvarEntry,
    error,
    isSuccess
  } = useGetEntryQuery({ entryUuid });
  const [getEntry] = useLazyGetEntryQuery();

  const refresh = useCallback(() => getEntry({ entryUuid }), [entryUuid, getEntry]);

  return (
    <>
      <Card>
        <CardBody className="d-flex justify-content-end">
          <Button color="primary" onClick={toggleUploadEntryFileModal}>
            <FormattedMessage id="entry.file.upload" />
          </Button>
          <UploadEntryFileModal
            entryUuid={entryUuid}
            isOpen={isUploadEntryFileModalOpen}
            toggle={toggleUploadEntryFileModal}
            onUploaded={refresh} />
        </CardBody>
        <ErrorAlert error={error} />
        {isSuccess && (
          <EntryFileTable
            entryUuid={entryUuid} entryFileList={jvarEntry.files}
            onUpdated={refresh} onDeleted={refresh} />
        )}
      </Card>
    </>
  );
}
