import { useMemo, useState } from 'react';
import Link from 'next/link';
import classNames from 'classnames';
import {
  Nav,
  Navbar as RSNavbar,
  NavItem,
  NavLink,
  UncontrolledTooltip,
} from 'reactstrap';

import {
  FormattedMessage
} from 'react-intl';

import { useToggleSidebar } from '../../hooks/navigation';

import s from './Navbar.module.scss';

export default function Navbar () {
  const toggleSidebar = useToggleSidebar(true);

  return (
    <RSNavbar className={classNames(s.navbar, 'd-print-none bg-white')}>
      <Nav className={s.navbar__nav}>
        <NavItem>
          <NavLink onClick={toggleSidebar} className={s.navbar__menu} id="toggleSidebar">
            <i className="la la-bars" />
          </NavLink>
          <UncontrolledTooltip popperClassName={s.navbar__menuToolTip} placement="bottom" target="toggleSidebar">
            <FormattedMessage id="sidebar.toggle_tooltip" />
          </UncontrolledTooltip>
        </NavItem>
      </Nav>
    </RSNavbar>
  );
}
