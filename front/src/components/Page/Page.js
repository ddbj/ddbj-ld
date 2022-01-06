import { useEffect } from 'react';
import classNames from 'classnames';
import { useRouter } from 'next/router';

import { useCurrentUser } from '../../hooks/auth';

import PageTitle from './PageTitle';
import PageHeader from './PageHeader';
import PageSection from './PageSection';
import { NavigatedPageSection, NavigatedPageContainer } from './navigatedPage';

import * as s from './Page.module.scss';

export default function Page ({ guestOnly = false, adminOnly = false, authorizedOnly = false, redirectTo = '/', ...props }) {
  const router = useRouter();
  const currentUser = useCurrentUser();

  useEffect(() => {
    if (
      (!guestOnly || !currentUser)
      && (!adminOnly || currentUser?.admin)
      && (!authorizedOnly || currentUser)
    ) return;

    router.replace(redirectTo);
  }, [adminOnly, authorizedOnly, guestOnly, redirectTo, router, currentUser]);

  return <main className={classNames(s.page)} {...props} />;
}

export function AuthorizedPage (props) {
  return <Page authorizedOnly {...props} />;
}

export function AdminPage (props) {
  return <Page adminOnly {...props} />;
}

export function GuestPage (props) {
  return <Page guestOnly {...props} />;
}

export {
  PageTitle,
  PageHeader,
  NavigatedPageSection,
  NavigatedPageContainer
};
