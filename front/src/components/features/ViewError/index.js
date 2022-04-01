import Head from 'next/head';
import { useMemo } from 'react';

import { RESPONSE_STATUS } from '../../../constants';

import { useErrorContents } from './hooks';
import { useTitle } from '../../../hooks/page';

import {
  BadRequestPageContent,
  InternalServerErrorPageContent,
  NotFoundPageContent,
  UnauthorizedPageContent,
  UnexpectedErrorPageContent
} from '../../parts/errorPageContents';

export default function ViewError ({ status }) {
  const { title } = useErrorContents(status);
  const pageTitle = useTitle(title);

  const content = useMemo(() => {
    switch(status) {
    case RESPONSE_STATUS.BAD_REQUEST :
      return <BadRequestPageContent />;
    case RESPONSE_STATUS.UNAUTHORIZED :
      return <UnauthorizedPageContent />;
    case RESPONSE_STATUS.NOT_FOUND :
      return <NotFoundPageContent />;
    case RESPONSE_STATUS.INTERNAL_SERVER_ERROR :
      return <InternalServerErrorPageContent />;
    default:
      return <UnexpectedErrorPageContent />;
    }
  }, [status]);

  return (
    <>
      <Head>
        <title>{pageTitle}</title>
      </Head>
      {content}
    </>
  );
}
