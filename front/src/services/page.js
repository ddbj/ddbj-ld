import { getServerSession } from 'next-auth';
import { nextAuthOptions } from '../services/nextauth/options';

import { PAGE_ROLE, RESPONSE_STATUS, RESPONSE_STATUS as STATUS } from '../constants';

const OK_PROPS = { status: STATUS.OK };
const UNAUTHORIZED_PROPS = { status: STATUS.UNAUTHORIZED };

export async function getPageProps (context, { pageRole }) {
  const session = await getServerSession(context, nextAuthOptions);

  if (pageRole === PAGE_ROLE.ADMIN) {
    if (!session?.account?.admin) return UNAUTHORIZED_PROPS;
    return OK_PROPS;
  }

  if (pageRole === PAGE_ROLE.USER) {
    if (!session) return UNAUTHORIZED_PROPS;
    return OK_PROPS;
  }

  if (pageRole === PAGE_ROLE.GUEST) {
    if (!!session) {
      const { res } = context;
      res.writeHead(RESPONSE_STATUS.TEMPORARY_REDIRECT, { Location: '/' });
      res.end();
      return {};
    }
    return OK_PROPS;
  }

  return OK_PROPS;
}

export function getPageServerSideProps(pageRole, props = {}) {
  return async function getServerSideProps(context) {
    const pageProps = await getPageProps(context, { pageRole });
    return {
      props: {
        ...props,
        ...pageProps
      }
    };
  };
}

export function getGusetPageServerSideProps (props = {}) {
  return getPageServerSideProps(PAGE_ROLE.GUEST, props);
}

export function getUserPageServerSideProps (props = {}) {
  return getPageServerSideProps(PAGE_ROLE.USER, props);
}

export function getAdminPageServerSideProps (props = {}) {
  return getPageServerSideProps(PAGE_ROLE.ADMIN, props);
}
