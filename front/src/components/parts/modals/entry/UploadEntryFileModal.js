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
  useUploadEntryFileMutation
} from '@/services/entryApi';

import {
  useUploadEntryFileValidationSchema
} from '@/hooks/entry';

import ErrorAlert from '@/components/parts/ErrorAlert';
import { FormFooter, FormPositiveActions, FormNegativeActions } from '../../form';
import UploadEntryFileFormFields from '../../FormFieldss/entry/UploadEntryFileFormFields';

export default function UploadEntryFileModal ({ isOpen, toggle, onUploaded, entryUuid }) {
  const intl = useIntl();

  const [uploadEntryFile, {
    isLoading: isUploading,
    isSuccess: isUploaded,
    error: uploadError
  }] = useUploadEntryFileMutation();

  const validationSchema = useUploadEntryFileValidationSchema();
  const initialValues = useMemo(() => validationSchema.cast({
    entryUuid
  }), [entryUuid, validationSchema]);

  const handleSubmit = useCallback(({ entryUuid, fileType, fileName, file }) => {
    uploadEntryFile({
      entryUuid,
      fileType,
      file,
      fileName
    });
  }, [uploadEntryFile]);

  useEffect(() => {
    if (!isUploaded) return;
    toast.success(intl.formatMessage({ id: 'entry.file.succeed_to_upload' }));
    toggle();
    onUploaded && onUploaded();
  }, [intl, isUploaded, onUploaded, toggle]);

  return (
    <Modal toggle={toggle} isOpen={isOpen}>
      <Formik validationSchema={validationSchema} initialValues={initialValues} onSubmit={handleSubmit}>
        {props => (
          <Form>
            <ModalHeader>
              <FormattedMessage id="entry.file.uploading" />
            </ModalHeader>
            <ModalBody>
              <UploadEntryFileFormFields />
              <ErrorAlert error={uploadError} />
            </ModalBody>
            <ModalFooter>
              <FormFooter>
                <FormPositiveActions>
                  <Button type="submit" color="primary" disabled={isUploading || !props.isValid}>
                    <FormattedMessage id="entry.file.upload" />
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
