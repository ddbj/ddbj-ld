import { useEffect } from 'react';
import { useRouter } from 'next/router';
import Head from 'next/head';
import { useIntl } from 'react-intl';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';

import { login, selectAuthState } from '../slices/authSlice';

import { useTitle } from '../hooks/page';

import Page from '../components/Page';
import ErrorAlert from '../components/ErrorAlert';
import Loading from '../components/Loading';

export default function Authorize () {
  const dispatch = useDispatch();
  const router = useRouter();
  const intl = useIntl();
  const { query: { code } } = useRouter();
  const { currentUser, error } = useSelector(selectAuthState);

  const title = useTitle(
    intl.formatMessage({ id: 'authorize' })
  );

  useEffect(() => {
    if (currentUser) {
      router.replace('/');
      return;
    }

    if (code) {
      dispatch(login({ code }));
      return;
    }
  }, [code, currentUser, dispatch, router]);

  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <Page>
        <ErrorAlert error={error} />
        {!error && <Loading />}
      </Page>
    </>
  );
}
