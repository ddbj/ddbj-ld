import { FormattedMessage } from 'react-intl';
import { Card, CardBody, Button } from 'reactstrap';

import { useGetEntryQuery } from '@/services/entryApi';
import { useModal } from '@/hooks/ui';
import ErrorAlert from '@/components/parts/ErrorAlert';
import EntryFileTable from '@/components/parts/entry/EntryFileTable';
import UploadEntryFileModal from '@/components/parts/entry/UploadEntryFileModal';

export default function ManageEntryFiles ({ entryUuid }) {
  const [
    isUploadEntryFileModalOpen,
    toggleUploadEntryFileModal
  ] = useModal(false);

  const {
    data: jvarEntry,
    error,
    isSuccess,
    refetch
  } = useGetEntryQuery({ entryUuid });

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
            onUploaded={refetch} />
        </CardBody>
        <ErrorAlert error={error} />
        {isSuccess && (
          <EntryFileTable
            entryUuid={entryUuid} entryFileList={jvarEntry.files}
            onUpdated={refetch} onDeleted={refetch} />
        )}
      </Card>
    </>
  );
}
