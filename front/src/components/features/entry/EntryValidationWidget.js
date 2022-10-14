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
  useValidateEntryMutation,
} from '@/services/entryApi';

import {
  useValidateEntryValidationSchema
} from '@/hooks/entry';

import Loading from '@/components/parts/Loading';
import ErrorAlert from '@/components/parts/ErrorAlert';
import {
  FormBody,
  FormFooter,
  FormPositiveActions
} from '@/components/parts/form';

import ValidateEntryFormFields from '@/components/parts/FormFieldss/entry/ValidateEntryFormFields';
import { useEffect } from 'react';
import { toast } from 'react-toastify';

export default function EntryValidationWidget ({ entryUuid }) {
  const {
    data: entry,
    error: getError,
    isLoading
  } = useGetEntryQuery({ entryUuid });

  const [validateEntry, {
    isSuccess: isValidated,
    isLoading: isValidating,
    error: validateError,
  }] = useValidateEntryMutation();

  const [getEntry] = useLazyGetEntryQuery();

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

  const refresh = useCallback(() => getEntry({ entryUuid }), [entryUuid, getEntry]);

  useEffect(() => {
    if (!isValidated) return;
    toast.success(intl.formatMessage({ id: 'entry.succeed_to_validate' }));
    refresh();
  }, [isValidated, refresh]);

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
                  <FormBody>
                    <ValidateEntryFormFields />
                    <ErrorAlert error={validateError} />
                  </FormBody>
                  <FormFooter>
                    <FormPositiveActions>
                      <Button color="primary" type="submit" disabled={isValidating || !props.isValid}>
                        <FormattedMessage id="entry.validate" />
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
              <FormattedMessage id="entry.not_validatable" />
            </div>
          </div>
        )}
      </Card>
    </>
  );
}
