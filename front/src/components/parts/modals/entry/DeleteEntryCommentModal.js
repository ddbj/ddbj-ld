import { useMemo, useCallback, useEffect } from 'react';
import { Formik, Form } from 'formik';
import { FormattedMessage, useIntl } from 'react-intl';
import { toast } from 'react-toastify';
import {
  Modal,
  ModalBody,
  ModalFooter,
  ModalHeader,
  Button
} from 'reactstrap';

import { useDeleteEntryCommentMutation } from '@/services/entryApi';

import { useDeleteEntryCommentValidationSchema } from '@/hooks/entry';

import ErrorAlert from '@/components/parts/ErrorAlert';
import {
  FormBody,
  FormFooter,
  FormPositiveActions,
  FormNegativeActions
} from '../../form';
import DeleteEntryCommentFormFields from '../../entry/DeleteEntryCommentFormFields';

export default function DeleteEntryCommentModal ({ isOpen, toggle, entryUuid, entryCommentUuid, onDeleted }) {
  const intl = useIntl();

  const [deleteEntryComment, {
    isLoading: isDeleting,
    isSuccess: isDeleted,
    error: deleteError
  }] = useDeleteEntryCommentMutation();

  const validationSchema = useDeleteEntryCommentValidationSchema();
  const initialValues = useMemo(() => validationSchema.cast({ entryUuid, entryCommentUuid }), [entryCommentUuid, entryUuid, validationSchema]);

  const handleSubmit = useCallback(({ entryUuid, entryCommentUuid }) => {
    deleteEntryComment({ entryUuid, entryCommentUuid });
  }, [deleteEntryComment]);

  useEffect(() => {
    if (!isDeleted) return;
    toast.success(intl.formatMessage({ id: 'entry.comment.succeed_to_delete' }));
    onDeleted && onDeleted();
  }, [isDeleted, onDeleted, intl]);

  return (
    <Modal isOpen={isOpen} toggle={toggle}>
      <Formik initialValues={initialValues} validationSchema={validationSchema} onSubmit={handleSubmit}>
        {props => (
          <Form>
            <ModalHeader>
              <FormattedMessage id="entry.comment.deleting"/>
            </ModalHeader>
            <ModalBody>
              <FormBody>
                <DeleteEntryCommentFormFields />
                <ErrorAlert error={deleteError} />
              </FormBody>
            </ModalBody>
            <ModalFooter>
              <FormFooter>
                <FormPositiveActions>
                  <Button type="submit" color="danger" disabled={isDeleting || !props.isValid}>
                    <FormattedMessage id="entry.comment.delete" />
                  </Button>
                </FormPositiveActions>
                <FormNegativeActions>
                  <Button color="secondary" outline type="button" onClick={toggle}>
                    <FormattedMessage id="cancel" />
                  </Button>
                </FormNegativeActions>
              </FormFooter>
            </ModalFooter>
          </Form>
        )}
      </Formik>
    </Modal>
  );
}
