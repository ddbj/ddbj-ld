import { Card } from 'reactstrap';
import { useGetEntryQuery } from '@/services/entryApi';
import ErrorAlert from '@/components/parts/ErrorAlert';
import JvarEntrySummaryPropertiesList from '@/components/parts/entry/jvar/JvarEntrySummaryPropertiesList';

export default function ViewJvarEntrySummary ({ entryUuid }) {
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
