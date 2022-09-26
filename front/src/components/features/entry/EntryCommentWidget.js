import { useCallback } from 'react';
import { Card } from 'reactstrap';

import {
  useGetEntryQuery,
  useLazyGetEntryQuery
} from '../../../services/entryApi';

import ErrorAlert from '@/components/parts/ErrorAlert';

import EntryCommentList from '../../parts/lists/entry/EntryCommentList';

export default function EntryCommentWidget ({ entryUuid }) {
  const {
    data: entry,
    isSuccess: isGot,
    error: getError
  } = useGetEntryQuery({ entryUuid });

  const [getEntry] = useLazyGetEntryQuery();

  const refresh = useCallback(() => getEntry({ entryUuid }), [getEntry, entryUuid]);

  return (
    <>
      <Card>
        <ErrorAlert error={getError} />
        {isGot && (
          <EntryCommentList
            entryUuid={entryUuid}
            entryCommentList={entry.comments}
            onUpdated={refresh} onDeleted={refresh} onCreated={refresh} />
        )}
      </Card>
    </>
  );
}
