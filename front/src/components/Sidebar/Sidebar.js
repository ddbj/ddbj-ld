import { useEffect, useMemo } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useRouter } from 'next/router';
import Link from 'next/link';
import classNames from 'classnames';
import {
  useIntl,
  FormattedMessage
} from 'react-intl';

import {
  setOpenedGroup,
  selectNavigationState
} from '../../slices/navigationSlice';

import { logout } from '../../slices/authSlice';

import { useAuthrizeUrl, useCurrentUser, useIsAdmin, useIsAuthorized } from '../../hooks/auth';
import { useToggleSidebar } from '../../hooks/navigation';

import {
  useOpenSidebar,
  useCloseSidebar,
} from '../../hooks/navigation';

import LinkGroup, { LinkItem, LinkItemButton } from './LinkGroup';

import * as s from './Sidebar.module.scss';

function CurrentUser () {

  const currentUser = useCurrentUser();

  const head = useMemo(() => currentUser.uid.charAt(0), []);

  return (
    <div className={s.currentUser}>
      <div className={s.currentUser__icon}>
        <span>{head}</span>
      </div>
      <div className={s.currentUser__uid}>{currentUser.uid}</div>
    </div>
  );
}

function ToggleButton () {
  const toggleSidebar = useToggleSidebar(true);

  return (
    <button className={s.toggleButton} onClick={toggleSidebar}>
      <i className="la la-bars" />
    </button>

  );
}

export default function Sidebar () {
  const dispatch = useDispatch();
  const { pathname, locale, query } = useRouter();
  const intl = useIntl();

  const isAdmin = useIsAdmin();
  const isAuthorized = useIsAuthorized();
  const authorizedUrl = useAuthrizeUrl();

  const {
    sidebarOpened
  } = useSelector(selectNavigationState);
  const openSidebar = useOpenSidebar(false);
  const closeSidebar = useCloseSidebar(false);

  useEffect(() => {
    dispatch(setOpenedGroup(pathname));
  }, [dispatch, pathname]);

  return (
    <div className={classNames(s.sidebar, { [s.sidebar__closed]: !sidebarOpened })}>
      <div className={s.sidebar__container}>
        <nav
          onMouseEnter={openSidebar} onMouseLeave={closeSidebar}
          className={classNames(s.sidebar__content, { [s.sidebar__container__closed]: !sidebarOpened })}>
          <ToggleButton />
          {isAuthorized && ( <CurrentUser /> )}
          <ul className={s.sidebar__nav}>
            <LinkGroup
              icon="flaticon-user" pathname="/account"
              title={intl.formatMessage({ id: 'account' })}>
              {isAuthorized ? (
                <>
                  <LinkItem href="/account">
                    <FormattedMessage id='account' />
                  </LinkItem>
                  <LinkItemButton onClick={() => dispatch(logout())}>
                    <FormattedMessage id='logout' />
                  </LinkItemButton>
                </>
              ) : (
                <>
                  <LinkItem href={authorizedUrl}>
                    <FormattedMessage id='login' />
                  </LinkItem>
                </>
              )}
            </LinkGroup>
            <LinkGroup
              icon="flaticon-app" pathname="/resource"
              title={intl.formatMessage({ id: 'resource' })}>
              <LinkItem href="/resource/search">
                <FormattedMessage id='resource.search' />
              </LinkItem>
            </LinkGroup>
            {isAuthorized && (
              <LinkGroup
                icon="flaticon-database" pathname='/entry'
                title={intl.formatMessage({ id: 'entry' })}>
                <LinkItem href="/entry/jvar">
                  <FormattedMessage id='entry.jvar' />
                </LinkItem>
                <LinkItem href="/entry/bio_sample">
                  <FormattedMessage id='entry.bio_sample' />
                </LinkItem>
              </LinkGroup>
            )}
            {isAdmin && (
              <LinkGroup
                icon="flaticon-users" pathname='/admin'
                title={intl.formatMessage({ id: 'admin' })}>
                <LinkItem href="/admin/user">
                  <FormattedMessage id='admin.user' />
                </LinkItem>
              </LinkGroup>
            )}
          </ul>
          <div className={s.group}>
            <div className={s.group__title}>
              <FormattedMessage id="language" />
            </div>
            <div className={s.actions}>
              <Link href={{ pathname, query }} locale={'ja'}>
                <a className={classNames('btn', 'btn-primary', 'btn-sm', { active: locale === 'ja' })}>
                  <FormattedMessage id="locale.ja" />
                </a>
              </Link>
              <Link href={{ pathname, query }} locale={'en'}>
                <a className={classNames('btn', 'btn-primary', 'btn-sm', { active: locale === 'en' })}>
                  <FormattedMessage id="locale.en" />
                </a>
              </Link>
            </div>
          </div>
        </nav>
      </div>
    </div>
  );
}
