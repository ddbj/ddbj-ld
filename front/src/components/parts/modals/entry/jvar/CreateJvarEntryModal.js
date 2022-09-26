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

import { useCreateEntryMutation } from '@/services/entryApi';

import { useCreateJvarEntryValidationSchema } from '@/hooks/entry/jvar';
import ErrorAlert from '@/components/parts/ErrorAlert';
import CreateJvarEntryFormFields from '@/components/parts/entry/jvar/CreateJvarEntryFormFields';

export default function CreateJvarEntryModal ({ isOpen, toggle, onCreated }) {
  const intl = useIntl();

  const [createEntry, {
    isLoading: isCreating,
    isSuccess: isCreated,
    error
  }] = useCreateEntryMutation();

  const validationSchema = useCreateJvarEntryValidationSchema();
  const initialValues = useMemo(() => validationSchema.cast({}), [validationSchema]);

  const handleSubmit = useCallback((entry) => {
    createEntry({ entry });
  }, [createEntry]);

  useEffect(() => {
    if (!isCreated) return;
    toast.success(intl.formatMessage({ id: 'entry.jvar.succeed_to_create' }));
    toggle();
    onCreated && onCreated();
  }, [intl, isCreated, onCreated, toggle]);

  return (
    <Modal toggle={toggle} isOpen={isOpen}>
      <Formik validationSchema={validationSchema} initialValues={initialValues} onSubmit={handleSubmit}>
        {props => (
          <Form>
            <ModalHeader>
              <FormattedMessage id="entry.jvar.creating" />
            </ModalHeader>
            <ModalBody>
              <FormBody>
                <CreateJvarEntryFormFields />
                <ErrorAlert error={error} />
              </FormBody>
            </ModalBody>
            <ModalFooter>
              <FormFooter>
                <FormPositiveActions>
                  <Button type="submit" color="primary" disabled={isCreating || !props.isValid}>
                    <FormattedMessage id="entry.jvar.create" />
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
