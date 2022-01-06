import {
  EntryActiveRequestLabel,
  EntryStatusLabel,
  EntryRequestTypeLabel
} from '../../labels/entry';

export function EntryActiveRequestCell ({ value }) {
  return <EntryActiveRequestLabel activeRequest={value} />;
}

export function EntryStatusCell ({ value }) {
  return (
    <EntryStatusLabel status={value} />
  );
}

export function EntryRequestType ({ value }) {
  return <EntryRequestTypeLabel requestType={value} />;
}
