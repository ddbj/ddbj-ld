import { Card } from 'reactstrap';
import { useGetEntryQuery } from '@/services/entryApi';
import ErrorAlert from '@/components/parts/ErrorAlert';
import EntryCommentList from '@/components/parts/entry/EntryCommentList';

export default function ManageEntryComments ({ entryUuid }) {
  const {
    data: entry,
    isSuccess: isGot,
    error: getError,
    refetch
  } = useGetEntryQuery({ entryUuid });

  return (
    <>
      <Card>
        <ErrorAlert error={getError} />
        {isGot && (
          <EntryCommentList
            entryUuid={entryUuid}
            entryCommentList={entry.comments}
            onUpdated={refetch} onDeleted={refetch} onCreated={refetch} />
        )}
      </Card>
    </>
  );
}
