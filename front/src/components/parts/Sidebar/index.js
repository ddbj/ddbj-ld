import { useMemo, useCallback } from 'react';
import Link from 'next/link';
import { useIntl, FormattedMessage } from 'react-intl';
import classNames from 'classnames';

import { LOCALE } from '../../../constants'

import LinkGroup, { LinkItem } from '../../parts/LinkGroup';

import { useGetLinkGroupProps, useGetLinkItemProps } from './hooks';

import * as s from './Sidebar.module.scss';

function CurrentUser ({ account }) {
  const head = useMemo(() => account?.uid?.charAt(0), [account.uid]);
  return (
    <div className={s.currentUser}>
      <div className={s.currentUser__icon}>
        <span>{head}</span>
      </div>
      <div className={s.currentUser__uid}>{account.uid}</div>
    </div>
  );
}

export default function Sidebar ({
  currentUser,
  router: {
    pathname,
    locale,
    query
  },
  // SideBar
  isOpened,
  onOpen,
  onClose,
  onToggle,
  // SideBarGroup
  openedGroupName,
  onToggleGroup,
}) {
  const intl = useIntl();

  const isAuthorized = !!currentUser;

  const linkGroupComponentOptions = {
    isSideBarOpened: isOpened,
    currentPathname: pathname
  };

  const handleMouseEnter = useCallback(() => {
    onOpen({ isSideBarStatic: true });
  }, [onOpen]);

  const getLinkGroupProps = useGetLinkGroupProps({
    openedGroupName: openedGroupName,
    onToggleGroup  : onToggleGroup,
    ...linkGroupComponentOptions
  });

  const getLinkItemProps = useGetLinkItemProps({
    closeSideBar: onClose,
    ...linkGroupComponentOptions
  });

  return (
    <div className={classNames(s.sidebar, { [s.sidebar__closed]: !isOpened })}>
      <nav
        onMouseEnter={handleMouseEnter}
        className={classNames(s.sidebar__container)}>
        <button className={s.toggleButton} onClick={onToggle}>
          <i className="la la-bars" />
        </button>
        {isAuthorized && <CurrentUser account={currentUser} />}
        <ul className={s.sidebar__nav}>
          <LinkGroup {...getLinkGroupProps({
            pathname: '/account',
            icon    : 'flaticon-user',
            title   : intl.formatMessage({ id: 'account' })
          })}>
            {isAuthorized ? (
              <>
                <LinkItem {...getLinkItemProps({ href: '/account' })}>
                  <FormattedMessage id="account.info" />
                </LinkItem>
                <LinkItem {...getLinkItemProps({ href: '/logout' })}>
                  <FormattedMessage id="logout" />
                </LinkItem>
              </>
            ) : (
              <>
                <LinkItem {...getLinkItemProps({ href: '/login' })}>
                  <FormattedMessage id="login" />
                </LinkItem>
              </>
            )}
          </LinkGroup>
          <LinkGroup {...getLinkGroupProps({
            pathname: '/resource',
            icon    : 'flaticon-app',
            title   : intl.formatMessage({ id: 'resource' })
          })}>
              <LinkItem {...getLinkItemProps({ href: '/resource/search' })}>
                <FormattedMessage id="resource.search" />
              </LinkItem>
          </LinkGroup>
          {isAuthorized && (
            <LinkGroup {...getLinkGroupProps({
              pathname: '/entry',
              icon    : 'flaticon-database',
              title   : intl.formatMessage({ id: 'entry' })
            })}>
              <LinkItem {...getLinkItemProps({ href: '/entry/jvar' })}>
                <FormattedMessage id="entry.jvar" />
              </LinkItem>
              <LinkItem {...getLinkItemProps({ href: '/entry/bio_sample' })}>
                <FormattedMessage id="entry.bio_sample" />
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
              <a className={classNames('btn', 'btn-primary', 'btn-sm', { active: locale === LOCALE.JA })}>
                <FormattedMessage id="locale.ja" />
              </a>
            </Link>
            <Link href={{ pathname, query }} locale={'en'}>
              <a className={classNames('btn', 'btn-primary', 'btn-sm', { active: locale === LOCALE.EN })}>
                <FormattedMessage id="locale.en" />
              </a>
            </Link>
          </div>
        </div>
      </nav>
    </div>
  );
}
