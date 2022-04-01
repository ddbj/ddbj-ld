
import { Page } from '../components/parts/pages';
import ViewError from '../components/features/ViewError';

export default function Error ({ status }) {
  return (
    <Page>
      <ViewError status={status} />
    </Page>
  );
}

export function getServerSideProps ({ res })  {
  const status = res ? res.statusCode : err ? err.statusCode : 404;
  return {
    props: { status }
  };
}
