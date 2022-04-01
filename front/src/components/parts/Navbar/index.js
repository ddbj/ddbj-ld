import PropTypes from 'prop-types';
import classNames from 'classnames';
import {
  Nav,
  Navbar as RSNavbar,
  NavItem,
  Button
} from 'reactstrap';

import s from './Navbar.module.scss';

import FontAwesome from '../icons/FontAwesome';

export default function Navbar ({ onToggleSideBar }) {
  return (
    <RSNavbar className={classNames(s.navbar, 'd-print-none bg-white')}>
      <Nav className={s.navbar__nav}>
        <NavItem>
          <Button color="link" onClick={onToggleSideBar} className={s.navbar__menu}>
            <FontAwesome name="bars" />
          </Button>
        </NavItem>
      </Nav>
    </RSNavbar>
  );
}

Navbar.propTypes = {
  onToggleSideBar: PropTypes.func
};
