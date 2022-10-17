import { useCallback, useMemo, useEffect } from 'react';
import { FormattedMessage } from 'react-intl';
import { Formik, Form } from 'formik';
import { Card, CardBody, Button } from 'reactstrap';
import { toast } from 'react-toastify';

import { useGetEntryQuery, useSubmitEntryMutation } from '@/services/entryApi';
import { useSubmitEntryValidationSchema } from '@/hooks/entry';

import Loading from '@/components/parts/Loading';
import ErrorAlert from '@/components/parts/ErrorAlert';
import SubmitEntryFormFields from '@/components/parts/entry/SubmitEntryFormFields';

export default function SubmitEntry ({ entryUuid }) {
  const {
    data: entry,
    error: getError,
    isLoading, refetch
  } = useGetEntryQuery({ entryUuid });

  const [submitEntry, {
    isSuccess: isSubmitted,
    error: submitError,
  }] = useSubmitEntryMutation();

  const validationSchema = useSubmitEntryValidationSchema();
  const initialValues = useMemo(() => validationSchema.cast({}), [validationSchema]);

  const isSubmittable = useMemo(() => {
    return entry?.menu?.submit;
  }, [entry]);

  const handleSubmit = useCallback(() => {
    submitEntry({ entryUuid });
  }, [entryUuid, submitEntry]);

  useEffect(() => {
    if (!isSubmitted) return;
    toast.success(intl.formatMessage({ id: 'entry.succeed_to_submit' }));
    refetch();
  }, [refetch, isSubmitted]);

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
                  <div className="d-flex flex-column gap-2">
                    <SubmitEntryFormFields />
                    <ErrorAlert error={submitError} />
                  </div>
                  <div className="d-flex flex-row-reverse justify-between">
                    <Button color="primary" type="submit" disabled={isValidating || !props.isValid}>
                      <FormattedMessage id="entry.submit" />
                    </Button>
                  </div>
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
