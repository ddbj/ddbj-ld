import Head from 'next/head';
import { FormattedMessage, useIntl } from 'react-intl';

import { PageTitle, AuthorizedPage } from '../components/Page/Page';
import { useCurrentUser } from '../hooks/auth';
import { useTitle } from '../hooks/page';

export default function Account () {
  const intl = useIntl();
  const currentUser = useCurrentUser();

  const title = useTitle(
    intl.formatMessage({ id: 'account' })
  );

  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <AuthorizedPage>
        <PageTitle>
          <FormattedMessage id="account" />
        </PageTitle>

        {currentUser && (
          <>
            {/* TODO: 削除 */}
            <div className="bg-light p-4 my-4 border rounded" style={{ maxWidth: '400px' }}>
              <pre className="m-0">
                <code>
                  {JSON.stringify(currentUser, null, 2)}
                </code>
              </pre>
            </div>
          </>
        )}
      </AuthorizedPage>
    </>
  );
}
