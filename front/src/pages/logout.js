import { useEffect } from 'react';
import { useRouter } from 'next/router';
import Head from 'next/head';
import { useIntl } from 'react-intl';

import { logout, selectAuthState } from '../slices/authSlice';

import { useTitle } from '../hooks/page';

import Page from '../components/Page';
import Loading from '../components/Loading';
import { useDispatch } from 'react-redux';
import { useSelector } from 'react-redux';

export default function Logout () {
  const dispatch = useDispatch();
  const { accessToken } = useSelector(selectAuthState);
  const router = useRouter();

  const intl = useIntl();

  const title = useTitle(
    intl.formatMessage({ id: 'logout' })
  );

  useEffect(() => {
    dispatch(logout());
  }, [dispatch]);

  useEffect(() => {
    if (accessToken) return;
    router.replace('/');
  }, [router, accessToken]);

  return (
    <>
      <Head>
        <title>{title}</title>
      </Head>
      <Page>
        <Loading />
      </Page>
    </>
  );
}
