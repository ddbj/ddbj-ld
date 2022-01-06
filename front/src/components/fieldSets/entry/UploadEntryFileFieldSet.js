import { useCallback } from 'react';
import { useFormikContext } from 'formik';
import { useDropzone } from 'react-dropzone';
import { FormattedMessage, useIntl } from 'react-intl';
import { Alert, Button } from 'reactstrap';

import { ENTRY_FILE_ACCEPT_EXTENSION, ENTRY_FILE_TYPE } from '../../../constants';

import { ReadOnlyLabelAndValueField } from '../../form/Field';

import { EntryFileTypeLabel } from '../../labels/entry';

function fileToEntryFileType (file) {
  switch (file.type) {
  case 'text/vcard':
  case 'application/gzip':
    return ENTRY_FILE_TYPE.VCF;
  case 'application/vnd.ms-excel':
  case 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet':
    return ENTRY_FILE_TYPE.WORKBOOK;
  }

  return file.type;
}

export default function UploadEntryFileFieldSet() {
  const intl = useIntl();

  const { setValues, values, errors,  } = useFormikContext();

  const handleDrop = useCallback(([file]) => {
    setValues(values => ({
      ...values,
      fileName: file.name,
      fileType: fileToEntryFileType(file),
      file    : file
    }));
  }, [setValues]);

  const {
    getRootProps,
    getInputProps,
    open:openFileDialog
  } = useDropzone({
    onDrop    : handleDrop,
    noClick   : true,
    noKeyboard: true,
    multiple  : false,
    accept    : Object.values(ENTRY_FILE_ACCEPT_EXTENSION).join(', ')
  });

  return (
    <div className="d-flex flex-column gap-3">
      <div className="border d-flex p-4 rounded-3 bg-light flex gap-3 flex-column justify-content-center align-items-center" {...getRootProps()}>
        <input {...getInputProps()} />
        <FormattedMessage id="entry.file.select.message" />
        <Button type="button" color="secondary" outline onClick={openFileDialog}>
          <FormattedMessage id="entry.file.select" />
        </Button>
      </div>
      {values.fileName && (
        <ReadOnlyLabelAndValueField
          label={intl.formatMessage({ id: 'entry.file.name' })}
          value={values.fileName} />
      )}
      {values.fileType && (
        <ReadOnlyLabelAndValueField
          label={intl.formatMessage({ id: 'entry.file.type' })}
          value={
            <EntryFileTypeLabel fileType={values.fileType} />
          } />
      )}
      {Object.values(errors).map((error, index) =>
        <Alert className="m-0" key={index} color='danger'>{error}</Alert>
      )}
    </div>
  );
}
