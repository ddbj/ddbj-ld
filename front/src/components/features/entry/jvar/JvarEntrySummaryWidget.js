import { Card } from 'reactstrap';

import { useGetEntryQuery } from '@/services/entryApi';

import ErrorAlert from '@/components/parts/ErrorAlert';
import JvarEntrySummaryPropertiesList from '@/components/parts/lists/entry/jvar/JvarEntrySummaryPropertiesList';

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
