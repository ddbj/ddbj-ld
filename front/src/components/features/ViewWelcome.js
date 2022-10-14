import { FormattedMessage } from 'react-intl';

export default function ViewWelcome () {
  return (
    <div className="p-4">
      <h1>
        <FormattedMessage id="welcome" />
      </h1>
    </div>
  );
}
