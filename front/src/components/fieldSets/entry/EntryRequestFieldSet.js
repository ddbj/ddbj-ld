import { useIntl } from 'react-intl';

import {
  ENTRY_REQUEST_TYPE
} from '../../../constants';

import {
  InputField,
  SelectField
} from '../../form/Field';

export default function EntryRequestFieldSet ({
  isCancellable = false,
  isPublishable = false,
  isUpdatable = false,
}) {
  const intl = useIntl();

  return (
    <>
      <SelectField label={intl.formatMessage({ id: 'entry.request.type' })} name="type">
        {isCancellable && (
          <option value={ENTRY_REQUEST_TYPE.CANCEL}>
            {intl.formatMessage({ id: 'entry.request.type.cancel' })}
          </option>
        )}
        {isPublishable && (
          <option value={ENTRY_REQUEST_TYPE.PUBLISH}>
            {intl.formatMessage({ id: 'entry.request.type.publish' })}
          </option>
        )}
        {isUpdatable && (
          <option value={ENTRY_REQUEST_TYPE.UPDATE}>
            {intl.formatMessage({ id: 'entry.request.type.update' })}
          </option>
        )}
      </SelectField>
      <InputField
        type="textarea" rows={5}
        name="comment"
        label={intl.formatMessage({ id: 'entry.request.comment' })}
        placeholder={intl.formatMessage({ id: 'entry.request.comment' })} />
    </>
  );
}