import { useIntl } from 'react-intl';
import { InputField } from '../fields';

export default function EntryCommentFormFields () {
  const intl = useIntl();

  return (
    <>
      <InputField
        type="textarea" rows={5}
        name="comment"
        label={intl.formatMessage({ id: 'entry.comment.body' })}
        placeholder={intl.formatMessage({ id: 'entry.comment.body' })} />
    </>
  );
}
