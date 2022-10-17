import { useCallback, useEffect, useMemo } from 'react';
import { FormattedMessage, useIntl } from 'react-intl';
import { Formik, Form } from 'formik';
import { toast } from 'react-toastify';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';

import { useGetEntryQuery, useDeleteEntryMutation } from '@/services/entryApi';
import { useDeleteEntryValidationSchema } from '@/hooks/entry';

import ErrorAlert from '@/components/parts/ErrorAlert';
import Loading from '@/components/parts/Loading';
import DeleteEntryFormFields from './DeleteEntryFormFields';

export default function DeleteEntryModal ({ isOpen, toggle, onDeleted, entryUuid }) {
  const intl = useIntl();

  const {
    isSuccess,
    isLoading
  } = useGetEntryQuery({ entryUuid }, { skip: !entryUuid });

  const [deleteEntry, {
    isLoading: isDeleting,
    isSuccess: isDeleted,
    error: deleteError
  }] = useDeleteEntryMutation();

  const validationSchema = useDeleteEntryValidationSchema();
  const initialValues = useMemo(() => validationSchema.cast({
    entryUuid
  }), [entryUuid, validationSchema]);

  const handleSubmit = useCallback(({ entryUuid }) => {
    deleteEntry({ entryUuid });
  }, [deleteEntry]);

  useEffect(() => {
    if (!isDeleted) return;
    toast.success(intl.formatMessage({ id: 'entry.succeed_to_delete' }));
    onDeleted && onDeleted();
  }, [intl, isDeleted, onDeleted, toggle]);

  return (
    <Modal toggle={toggle} isOpen={isOpen}>
      {isLoading && <Loading />}
      {isSuccess && (
        <Formik validationSchema={validationSchema} initialValues={initialValues} onSubmit={handleSubmit}>
          {props => (
            <Form>
              <ModalHeader>
                <FormattedMessage id="entry.deletion" />
              </ModalHeader>
              <ModalBody>
                <div className="d-flex flex-column">
                  <DeleteEntryFormFields  />
                  <ErrorAlert error={deleteError} />
                </div>
              </ModalBody>
              <ModalFooter>
                <div className="d-flex flex-row-reverse justify-between">
                  <Button type="submit" color="danger" disabled={isDeleting || !props.isValid}>
                    <FormattedMessage id="entry.delete" />
                  </Button>
                  <Button color="secondary" outline type="button" onClick={toggle}>
                    <FormattedMessage id="cancel" />
                  </Button>
                </div>
              </ModalFooter>
            </Form>
          )}
        </Formik>
      )}
    </Modal>
  );
}
