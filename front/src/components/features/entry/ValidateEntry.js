import { useEffect, useCallback, useMemo } from 'react';
import { toast } from 'react-toastify';
import { FormattedMessage } from 'react-intl';
import { Formik, Form } from 'formik';
import { Card, CardBody, Button } from 'reactstrap';

import { useGetEntryQuery, useValidateEntryMutation } from '@/services/entryApi';
import { useValidateEntryValidationSchema } from '@/hooks/entry';
import Loading from '@/components/parts/Loading';
import ErrorAlert from '@/components/parts/ErrorAlert';
import ValidateEntryFormFields from '@/components/parts/entry/ValidateEntryFormFields';

export default function ValidateEntry ({ entryUuid }) {
  const {
    data: entry,
    error: getError,
    isLoading,
    refetch
  } = useGetEntryQuery({ entryUuid });

  const [validateEntry, {
    isSuccess: isValidated,
    isLoading: isValidating,
    error: validateError,
  }] = useValidateEntryMutation();

  const validationSchema = useValidateEntryValidationSchema();
  const initialValues = useMemo(() => validationSchema.cast({
    entryUuid
  }), [entryUuid, validationSchema]);

  const isValidatable = useMemo(() => {
    return entry?.menu?.validate;
  }, [entry]);

  const handleSubmit = useCallback(({ entryUuid }) => {
    validateEntry({ entryUuid });
  }, [validateEntry]);

  useEffect(() => {
    if (!isValidated) return;
    toast.success(intl.formatMessage({ id: 'entry.succeed_to_validate' }));
    refetch();
  }, [isValidated, refetch]);

  return (
    <>
      <Card>
        {isLoading && <Loading />}
        <ErrorAlert error={getError} />
        {isValidatable ? (
          <CardBody>
            <Formik  onSubmit={handleSubmit} validationSchema={validationSchema} initialValues={initialValues}>
              {props => (
                <Form>
                  <div className="d-flex flex-column gap-2">
                    <ValidateEntryFormFields />
                    <ErrorAlert error={validateError} />
                  </div>
                  <div className="d-flex flex-row-reverse justify-between">
                    <Button color="primary" type="submit" disabled={isValidating || !props.isValid}>
                      <FormattedMessage id="entry.validate" />
                    </Button>
                  </div>
                </Form>
              )}
            </Formik>
          </CardBody>
        ) : (
          <div className="py-5">
            <div className="text-muted text-center">
              <FormattedMessage id="entry.not_validatable" />
            </div>
          </div>
        )}
      </Card>
    </>
  );
}
