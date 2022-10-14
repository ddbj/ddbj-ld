import Link from 'next/link';
import classNames from 'classnames';
import { useIntl, FormattedMessage } from 'react-intl';

import { LOCALE } from '@/constants';

import  {
  NavigationLink,
  NavigationLinkGroups,
  NavigationLinkGroup
} from '../navigationLinks';

import * as s from './SideBar.module.scss';
import { useRouter } from 'next/router';

export default function SideBar ({ isOpened, toggle, className = '' }) {
  const intl = useIntl();
  const router = useRouter();
  const { locale, pathname, query } = router;

  const content = (
    <>
      <div className={s.SideBar__Head}>
        <button className={s.SideBar__CloseButton} onClick={toggle}>
          <i className={classNames('bi-x', s.SideBar__CloseButtonIcon)} />
        </button>
      </div>
      <NavigationLinkGroups router={router}>
        <NavigationLinkGroup
          title={intl.formatMessage({ id: 'resource' })} pathname="/resource">
          <NavigationLink href="/resource/search" exact>
            <FormattedMessage id="resource.search" />
          </NavigationLink>
        </NavigationLinkGroup>
        <NavigationLinkGroup title={intl.formatMessage({ id: 'entry' })} pathname="/entry">
          <NavigationLink href="/entry/bioproject">
            <FormattedMessage id="entry.bioproject.list" />
          </NavigationLink>
          <NavigationLink href="/entry/biosample">
            <FormattedMessage id="entry.biosample.list" />
          </NavigationLink>
          <NavigationLink href="/entry/jvar">
            <FormattedMessage id="entry.jvar.list" />
          </NavigationLink>
        </NavigationLinkGroup>
      </NavigationLinkGroups>
      <div className={s.Language}>
        <div className={s.Language__Title}>
          <FormattedMessage id="language" />
        </div>
        <div className={s.Language__Links}>
          <Link href={{ pathname, query }} locale={LOCALE.JA} passHref>
            <a className={classNames('btn btn-primary', {
              'active': locale === LOCALE.JA
            })}>
              <FormattedMessage id="locale.ja" />
            </a>
          </Link>
          <Link href={{ pathname, query }} locale={LOCALE.EN} passHref>
            <a className={classNames('btn btn-primary', {
              'active': locale === LOCALE.EN
            })}>
              <FormattedMessage id="locale.en" />
            </a>
          </Link>
        </div>
      </div>
    </>
  );

  return (
    <>
      <div className={classNames(s.SideBar__Backdrop, { [s.SideBar__Backdrop__Shown]: isOpened })} onClick={toggle} />
      <nav className={classNames(s.SideBar, { [s.SideBar__Opened]: isOpened }, className)}>{content}</nav>
      <nav className={classNames(s.SideBar, s.SideBar__Expanded, className)}>{content}</nav>
    </>
  );
}
