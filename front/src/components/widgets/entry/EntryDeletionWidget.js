import { useEffect, useCallback, useMemo } from 'react';
import { useRouter } from 'next/router';
import { FormattedMessage } from 'react-intl';
import { Formik, Form } from 'formik';
import { toast } from 'react-toastify';

import {
  Card, CardBody,
  Button,
} from 'reactstrap';

import { useGetEntryQuery } from '../../../services/entryApi';

import { useModal } from '../../../hooks/ui';

import Loading from '../../Loading';
import ErrorAlert from '../../ErrorAlert';

import DeleteEntryModal from '../../modals/entry/DeleteEntryModal';

export default function EntryDeletionWidget ({ entryUuid }) {
  const router = useRouter();

  const [
    isDeleteEntryModalOpen,
    toggleDeleteEntryModal
  ] = useModal(false);

  const {
    isLoading,
    error: getError,
  } = useGetEntryQuery({ entryUuid });

  const handleDeleted = useCallback(() => {
    router.replace('/entry/jvar');
  }, [router]);

  return (
    <>
      <Card>
        {isLoading && <Loading />}
        <ErrorAlert error={getError} />
        <CardBody>
          <FormattedMessage id="entry.delete.message" />
          <div className="d-flex justify-content-end">
            <Button color="danger" onClick={toggleDeleteEntryModal}>
              <FormattedMessage id="entry.delete" />
            </Button>
            <DeleteEntryModal
              entryUuid={entryUuid}
              isOpen={isDeleteEntryModalOpen}
              toggle={toggleDeleteEntryModal}
              onDeleted={handleDeleted} />
          </div>
        </CardBody>
      </Card>
    </>
  );
}
