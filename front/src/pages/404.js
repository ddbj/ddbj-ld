
import { Page } from '../components/parts/pages';
import ViewError from '../components/features/ViewError';

const NOT_FOUND_STATUS_CODE = 404;

export default function NotFound () {
  return (
    <Page>
      <ViewError statusCode={NOT_FOUND_STATUS_CODE} />
    </Page>
  );
}
