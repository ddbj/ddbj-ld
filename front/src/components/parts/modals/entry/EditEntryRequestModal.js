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

import {
  useGetEntryQuery,
  useUpdateEntryRequestMutation,
} from '@/services/entryApi';

import {
  useEntryRequestMenu,
  useUpdateEntryRequestValidationSchema
} from '@/hooks/entry';

import {
  FormFooter,
  FormPositiveActions,
  FormNegativeActions,
  FormBody
} from '../../form';
import ErrorAlert from '@/components/parts/ErrorAlert';
import EntryRequestFormFields from '../../FormFieldss/entry/EntryRequestFormFields';

export default function EditEntryRequestModal ({ isOpen, toggle, onSaved, entryUuid, entryRequestUuid }) {
  const intl = useIntl();

  const {
    data: entry,
    isSuccess
  } = useGetEntryQuery({ entryUuid }, { skip: !entryUuid });

  const entryRequest = useMemo(() => entry?.requests.find(entryRequest => entryRequest.uuid === entryRequestUuid), [entry?.requests, entryRequestUuid]);

  const [updateEntryRequest, {
    isLoading: isUpdating,
    isSuccess: isUpdated,
    error
  }] = useUpdateEntryRequestMutation();

  const validationSchema = useUpdateEntryRequestValidationSchema();
  const initialValues = useMemo(() => validationSchema.cast({
    entryUuid,
    entryRequestUuid: entryRequestUuid,
    type            : entryRequest?.type,
    comment         : entryRequest?.comment,
  }), [entryRequest?.comment, entryRequest?.type, entryRequestUuid, entryUuid, validationSchema]);

  const handleSubmit = useCallback(({
    entryUuid,
    entryRequestUuid,
    type,
    comment
  }) => {
    updateEntryRequest({
      entryUuid,
      entryRequestUuid,
      entryRequest: {
        type,
        comment
      }
    });
  }, [updateEntryRequest]);

  useEffect(() => {
    if (!isUpdated) return;
    toast.success(intl.formatMessage({ id: 'entry.request.succeed_to_save' }));
    toggle();
    onSaved && onSaved();
  }, [intl, isUpdated, onSaved, toggle]);

  const {
    isCancellable,
    isUpdatable,
    isPublishable
  } = useEntryRequestMenu(entry);

  return (
    <Modal toggle={toggle} isOpen={isOpen}>
      {isSuccess && (
        <Formik validationSchema={validationSchema} initialValues={initialValues} onSubmit={handleSubmit}>
          {props => (
            <Form>
              <ModalHeader>
                <FormattedMessage id="entry.request.editing" />
              </ModalHeader>
              <ModalBody>
                <FormBody>
                  <EntryRequestFormFields
                    isCancellable={isCancellable}
                    isUpdatable={isUpdatable}
                    isPublishable={isPublishable}
                  />
                  <ErrorAlert error={error} />
                </FormBody>
              </ModalBody>
              <ModalFooter>
                <FormFooter>
                  <FormPositiveActions>
                    <Button type="submit" color="primary" disabled={isUpdating || !props.isValid}>
                      <FormattedMessage id="entry.request.save" />
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
      )}
    </Modal>
  );
}
