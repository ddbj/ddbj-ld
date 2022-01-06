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
  useCreateEntryRequestMutation,
} from '../../../services/entryApi';

import {
  useEntryRequestMenu,
  useCreateEntryRequestValidationSchema
} from '../../../hooks/entry';

import {
  FormFooter,
  FormPositiveActions,
  FormNegativeActions,
  FormBody
} from '../../form';
import ErrorAlert from '../../ErrorAlert';
import EntryRequestFieldSet from '../../fieldSets/entry/EntryRequestFieldSet';

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

  const handleSubmit = useCallback(({
    entryUuid,
    type,
    comment
  }) => {
    createRequest({
      entryUuid,
      entryRequest: {
        type,
        comment
      }
    });
  }, [createRequest]);

  useEffect(() => {
    if (!isCreated) return;
    toast.success(intl.formatMessage({ id: 'entry.request.succeed_to_create' }));
    toggle();
    onCreated && onCreated();
  }, [intl, isCreated, onCreated, toggle]);

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
                <FormattedMessage id="entry.request.creating" />
              </ModalHeader>
              <ModalBody>
                <FormBody>
                  <EntryRequestFieldSet
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
                    <Button type="submit" color="primary" disabled={isCreating || !props.isValid}>
                      <FormattedMessage id="entry.request.create" />
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
