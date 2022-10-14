import { useCallback, useEffect, useMemo } from 'react';
import { FormattedMessage, useIntl } from 'react-intl';
import { Formik, Form } from 'formik';
import { toast } from 'react-toastify';
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter
} from 'reactstrap';

import { useDeleteEntryRequestMutation, useGetEntryQuery } from '@/services/entryApi';
import { useDeleteEntryRequestValidationSchema } from '@/hooks/entry';

import ErrorAlert from '@/components/parts/ErrorAlert';
import Loading from '@/components/parts/Loading';
import DeleteEntryRequestFormFields from '../../FormFieldss/entry/DeleteEntryRequestFormFields';

export default function DeleteEntryRequestModal ({ isOpen, toggle, onDeleted, entryUuid, entryRequestUuid }) {
  const intl = useIntl();

  const {
    data: entry,
    isSuccess,
    isLoading
  } = useGetEntryQuery({ entryUuid }, { skip: !entryUuid });

  const entryRequest = useMemo(() => entry?.requests.find(request => request.uuid === entryRequestUuid), [entry, entryRequestUuid]);

  const [deleteEntryRequest, {
    isLoading: isDeleting,
    isSuccess: isDeleted,
    error: deleteError
  }] = useDeleteEntryRequestMutation();

  const validationSchema = useDeleteEntryRequestValidationSchema();
  const initialValues = useMemo(() => validationSchema.cast({
    entryUuid,
    entryRequestUuid
  }), [entryRequestUuid, entryUuid, validationSchema]);

  const handleSubmit = useCallback(({ entryUuid, entryRequestUuid }) => {
    deleteEntryRequest({ entryUuid, entryRequestUuid });
  }, [deleteEntryRequest]);

  useEffect(() => {
    if (!isDeleted) return;
    toast.success(intl.formatMessage({ id: 'entry.request.succeed_to_delete' }));
    toggle();
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
                <FormattedMessage id="entry.file.deleting" />
              </ModalHeader>
              <ModalBody>
                <DeleteEntryRequestFormFields entryRequest={entryRequest} />
                <ErrorAlert error={deleteError} />
              </ModalBody>
              <ModalFooter className="d-flex flex-row-reverse justify-between">
                <Button type="submit" color="danger" disabled={isDeleting || !props.isValid}>
                  <FormattedMessage id="entry.request.delete" />
                </Button>
                <Button color="secondary" outline type="button" onClick={toggle}>
                  <FormattedMessage id="cancel" />
                </Button>
              </ModalFooter>
            </Form>
          )}
        </Formik>
      )}
    </Modal>
  );
}
