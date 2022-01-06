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
  useRejectEntryRequestMutation,
  useApplyEntryRequestMutation
} from '../../../services/entryApi';

import {
  useApplyEntryRequestValidationSchema,
  useRejectEntryRequestValidationSchema
} from '../../../hooks/entry';

import {
  FormFooter,
  FormPositiveActions,
  FormNegativeActions,
  FormBody
} from '../../form';
import ErrorAlert from '../../ErrorAlert';
import CreateEntryRequestFieldSet from '../../fieldSets/entry/CreateEntryRequestFieldSet';

export default function CreateEntryRequestModal ({ isOpen, toggle, entryUuid, entryRequestUuid, onApplied, onRejected }) {
  const intl = useIntl();

  const {
    data: entry,
    isSuccess
  } = useGetEntryQuery({ entryUuid }, { skip: !entryUuid });

  const [applyRequest, {
    isLoading: isApplying,
    isSuccess: isApplied,
    error: applyError
  }] = useApplyEntryRequestMutation();
  const applyValidationSchema = useApplyEntryRequestValidationSchema();
  const applyInitialValues = useMemo(() => applyValidationSchema.cast({ entryUuid, entryRequestUuid }), [applyValidationSchema, entryRequestUuid, entryUuid]);
  const handleApply = useCallback(({ entryUuid, entryRequestUuid }) => {
    applyRequest({
      entryUuid,
      entryRequestUuid
    });
  }, [applyRequest]);
  useEffect(() => {
    if (!isApplied) return;
    toast.success(intl.formatMessage({ id: 'entry.request.succeed_to_apply' }));
    toggle();
    onApplied && onApplied();
  }, [intl, isApplied, onApplied, toggle]);

  const [rejectRequest, {
    isLoading: isRejecting,
    isSuccess: isRejected,
    error: rejectError
  }] = useRejectEntryRequestMutation();
  const rejectValidationSchema = useRejectEntryRequestValidationSchema();
  const rejectInitialValues = useMemo(() => rejectValidationSchema.cast({ entryUuid, entryRequestUuid }), [rejectValidationSchema, entryRequestUuid, entryUuid]);
  useEffect(() => {
    if (!isRejected) return;
    toast.success(intl.formatMessage({ id: 'entry.request.succeed_to_reject' }));
    toggle();
    onRejected && onRejected();
  }, [intl, isRejected, onApplied, onRejected, toggle]);

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
                  <CreateEntryRequestFieldSet
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
