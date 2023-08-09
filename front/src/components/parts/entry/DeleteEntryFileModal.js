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

import { useDeleteEntryFileMutation, useGetEntryQuery } from '@/services/entryApi';
import { useDeleteEntryFileValidationSchema } from '@/hooks/entry';

import ErrorAlert from '@/components/parts/ErrorAlert';
import Loading from '@/components/parts/Loading';
import DeleteEntryFileFormFields from './DeleteEntryFileFormFields';

export default function DeleteEntryFileModal ({ isOpen, toggle, onDeleted, entryUuid, entryFileUuid }) {
  const intl = useIntl();

  const {
    data: entry,
    isSuccess,
    isLoading
  } = useGetEntryQuery({ entryUuid }, { skip: !entryUuid });
  const entryFile = useMemo(() => entry?.files.find(file => file.uuid === entryFileUuid), [entry, entryFileUuid]);

  const [deleteEntryFile, {
    isLoading: isDeleting,
    isSuccess: isDeleted,
    error: deleteError
  }] = useDeleteEntryFileMutation();

  const validationSchema = useDeleteEntryFileValidationSchema();
  const initialValues = useMemo(() => validationSchema.cast({
    entryUuid,
    fileType: entryFile?.type,
    fileName: entryFile?.name,
  }), [entryFile?.name, entryFile?.type, entryUuid, validationSchema]);

  const handleSubmit = useCallback(({ entryUuid, fileType, fileName }) => {
    deleteEntryFile({ entryUuid, fileType, fileName });
  }, [deleteEntryFile]);

  useEffect(() => {
    if (!isDeleted) return;
    toast.success(intl.formatMessage({ id: 'entry.file.succeed_to_delete' }));
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
                <div className="d-flex flex-column gap-2">
                  <DeleteEntryFileFormFields entryFile={entryFile} />
                  <ErrorAlert error={deleteError} />
                </div>
              </ModalBody>
              <ModalFooter>
                <div className="d-flex flex-row-reverse justify-between">
                  <Button type="submit" color="danger" disabled={isDeleting || !props.isValid}>
                    <FormattedMessage id="entry.file.delete" />
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
