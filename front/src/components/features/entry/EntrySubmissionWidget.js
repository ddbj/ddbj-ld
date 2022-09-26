import { useCallback, useMemo } from 'react';
import { FormattedMessage } from 'react-intl';
import { Formik, Form } from 'formik';
import {
  Card, CardBody,
  Button,
} from 'reactstrap';

import {
  useGetEntryQuery,
  useLazyGetEntryQuery,
  useSubmitEntryMutation,
} from '../../../services/entryApi';

import {
  useSubmitEntryValidationSchema
} from '../../../hooks/entry';

import Loading from '../../parts/Loading';
import ErrorAlert from '@/components/parts/ErrorAlert';
import {
  FormBody,
  FormFooter,
  FormPositiveActions
} from '../../parts/form';

import SubmitEntryFormFields from '../../parts/FormFieldss/entry/SubmitEntryFormFields';
import { useEffect } from 'react';
import { toast } from 'react-toastify';

export default function EntrySubmissionWidget ({ entryUuid }) {
  const {
    data: entry,
    error: getError,
    isLoading
  } = useGetEntryQuery({ entryUuid });

  const [submitEntry, {
    isSuccess: isSubmitted,
    isLoading: isSubmitting,
    error: submitError,
  }] = useSubmitEntryMutation();

  const [getEntry] = useLazyGetEntryQuery();

  const validationSchema = useSubmitEntryValidationSchema();
  const initialValues = useMemo(() => validationSchema.cast({}), [validationSchema]);

  const isSubmittable = useMemo(() => {
    return entry?.menu?.submit;
  }, [entry]);

  const handleSubmit = useCallback(() => {
    submitEntry({ entryUuid });
  }, [entryUuid, submitEntry]);

  const refresh = useCallback(() => getEntry({ entryUuid }), [entryUuid, getEntry]);

  useEffect(() => {
    if (!isSubmitted) return;
    toast.success(intl.formatMessage({ id: 'entry.succeed_to_submit' }));
    refresh();
  }, [refresh, isSubmitted]);

  return (
    <>
      <Card>
        {isLoading && <Loading />}
        <ErrorAlert error={getError} />
        {isSubmittable ? (
          <CardBody>
            <Formik  onSubmit={handleSubmit} validationSchema={validationSchema} initialValues={initialValues}>
              {props => (
                <Form>
                  <FormBody>
                    <SubmitEntryFormFields />
                    <ErrorAlert error={submitError} />
                  </FormBody>
                  <FormFooter>
                    <FormPositiveActions>
                      <Button color="primary" type="submit" disabled={isValidating || !props.isValid}>
                        <FormattedMessage id="entry.submit" />
                      </Button>
                    </FormPositiveActions>
                  </FormFooter>
                </Form>
              )}
            </Formik>
          </CardBody>
        ) : (
          <div className="py-5">
            <div className="text-muted text-center">
              <FormattedMessage id="entry.not_submittable" />
            </div>
          </div>
        )}
      </Card>
    </>
  );
}
