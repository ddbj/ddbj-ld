import { FormattedMessage } from 'react-intl';

export default function NotFoundPage () {
  return (
    <div className="min-vh-100 p-4 py-5">
      <h1 className="mb-4">
        <FormattedMessage id="error.not_found" />
      </h1>
      <p>
        <FormattedMessage id="error.not_found.message" />
      </p>
    </div>
  );
}
