import { useIntl } from 'react-intl';
import { InputField } from '../../form/Field';

export default function EntryCommentFieldSet () {
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
