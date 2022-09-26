import Link from 'next/link';
import classNames from 'classnames';
import { Collapse } from 'reactstrap';

import {
  Provider,
  useNavigationLink,
  useNavigationLinkGroup
} from './hooks';

import * as s from './navigationLinks.module.scss';

export function NavigationButton ({ ...props }) {
  return (
    <button className={classNames(s.NavigationItem)}  {...props} />
  );
}

export function NavigationLink ({ href, exact = false, ...props }) {
  const {
    isActive
  } = useNavigationLink({ href, exact });

  return (
    <Link href={href}>
      <a className={classNames(s.NavigationItem, { [s.NavigationItem__Active]: isActive })} {...props} />
    </Link>
  );
}

export function NavigationLinkGroup ({ title, pathname, children, ...props }) {
  const {
    toggle,
    isOpened,
    isActive
  } = useNavigationLinkGroup({ pathname });

  return (
    <li className={classNames(s.NavigationLinkGroup, {
      [s.NavigationLinkGroup__Active]: isActive
    })} onClick={toggle} {...props}>
      <div className={s.NavigationLinkGroup__Head}>
        <div className={s.NavigationLinkGroup__Title}>{title}</div>
        <i className={classNames('bi-chevron-right', s.NavigationLinkGroup__HeadIcon, {
          [s.NavigationLinkGroup__HeadIcon__Opened]: isOpened,
        })} />
      </div>
      <Collapse isOpen={isOpened}>
        <ul className={s.NavigationLinkGroup__LinkList}>
          {children}
        </ul>
      </Collapse>
    </li>
  );
}

export function NavigationLinkGroups ({ router, ...props }) {
  return (
    <Provider router={router}>
      <ul className={s.NavigationLinkGroups} {...props} />
    </Provider>
  );
}
