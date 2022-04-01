import { getServerSession } from 'next-auth';

import { RESPONSE_STATUS } from '../constants';

import { nextAuthOptions } from '../services/nextauth/options';

export default function Home () {
  return null;
}

export async function getServerSideProps(context) {
  const session = await getServerSession(context, nextAuthOptions);
  const { res } = context;
  const isAuthorized = !!session;
  const redirectTo = isAuthorized ? '/account' : '/resource/search';
  res.writeHead(RESPONSE_STATUS.TEMPORARY_REDIRECT, { Location: redirectTo });
  res.end();
  return {
    props: {}
  };
}
