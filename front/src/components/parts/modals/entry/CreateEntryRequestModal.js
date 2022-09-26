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

import { useGetEntryQuery, useCreateEntryRequestMutation } from '@/services/entryApi';
import { useEntryRequestMenu, useCreateEntryRequestValidationSchema } from '@/hooks/entry';

import Stack from '@/components/parts/Stack';
import ErrorAlert from '@/components/parts/ErrorAlert';
import EntryRequestFormFields from '@/components/parts/entry/EntryRequestFormFields';

export default function CreateEntryRequestModal ({ isOpen, toggle, onCreated, entryUuid }) {
  const intl = useIntl();

  const {
    data: entry,
    isSuccess
  } = useGetEntryQuery({ entryUuid }, { skip: !entryUuid });

  const [createRequest, {
    isLoading: isCreating,
    isSuccess: isCreated,
    error
  }] = useCreateEntryRequestMutation();

  const validationSchema = useCreateEntryRequestValidationSchema();
  const initialValues = useMemo(() => validationSchema.cast({
    entryUuid
  }), [entryUuid, validationSchema]);

  const menu = useEntryRequestMenu(entry);

  const handleSubmit = ({ entryUuid, type, comment }) => {
    createRequest({
      entryUuid,
      entryRequest: { type, comment }
    });
  };

  useEffect(() => {
    if (!isCreated) return;
    toast.success(intl.formatMessage({ id: 'entry.request.succeed_to_create' }));
    toggle();
    onCreated && onCreated();
  }, [intl, isCreated, onCreated, toggle]);

  return (
    <Modal toggle={toggle} isOpen={isOpen}>
      {isSuccess && (
        <Formik validationSchema={validationSchema} initialValues={initialValues} onSubmit={handleSubmit}>
          {props => (
            <Form>
              <ModalHeader>
                <FormattedMessage id="entry.request.creating" />
              </ModalHeader>
              <ModalBody>
                <Stack direction="column" gap={2}>
                  <EntryRequestFormFields {...menu} />
                  <ErrorAlert error={error} />
                </Stack>
              </ModalBody>
              <ModalFooter>
                <Stack direction="row-reverse" justifyContent="between" gap={2}>
                  <Button type="submit" color="primary" disabled={isCreating || !props.isValid}>
                    <FormattedMessage id="entry.request.create" />
                  </Button>
                  <Button color="secondary" outline type="button" onClick={toggle}>
                    <FormattedMessage id="cancel" />
                  </Button>
                </Stack>
              </ModalFooter>
            </Form>
          )}
        </Formik>
      )}
    </Modal>
  );
}
