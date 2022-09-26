import { FormattedMessage } from 'react-intl';
import { Alert } from 'reactstrap';
import { useFormikContext } from 'formik';

import { JVAR_ENTRY_TYPE } from '@/constants';
import { CheckBoxField, FieldContainer } from '@/components/parts/fields';

export default function CreateJvarEntryFormFields () {
  const {
    errors
  } = useFormikContext();

  return (
    <>
      <FieldContainer>
        <CheckBoxField name="type" type="radio" value={JVAR_ENTRY_TYPE.SNP}>
          <FormattedMessage id="entry.jvar.type.snp" />
        </CheckBoxField>
        <CheckBoxField name="type" type="radio" value={JVAR_ENTRY_TYPE.SV}>
          <FormattedMessage id="entry.jvar.type.sv" />
        </CheckBoxField>
      </FieldContainer>
      {errors.type && (
        <Alert color="danger">{errors.type}</Alert>
      )}
    </>
  );
}
