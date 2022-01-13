import { Card } from 'reactstrap';

import { useGetEntryQuery } from '../../../../services/entryApi';

import ErrorAlert from '../../../ErrorAlert';
import JvarEntrySummaryPropertiesList from '../../../lists/entry/jvar/JvarEntrySummaryPropertiesList';

export default function JvarEntrySummaryWidget ({ entryUuid }) {
  const {
    data: jvarEntry,
    error,
    isSuccess
  } = useGetEntryQuery({ entryUuid });

  return (
    <>
      <Card>
        <ErrorAlert error={error} />
        {isSuccess && <JvarEntrySummaryPropertiesList jvarEntry={jvarEntry} />}
      </Card>
    </>
  );
}
