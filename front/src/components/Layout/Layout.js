import classNames from 'classnames';

import Navbar from '../Navbar';
import Sidebar from '../Sidebar';

import s from './Layout.module.scss';
import { useSelector } from 'react-redux';
import { selectNavigationState } from '../../slices/navigationSlice';

export default function Layout ({ children }){

  const {
    sidebarStatic,
    sidebarOpened
  } = useSelector(selectNavigationState);

  return (
    <div className={classNames(
      s.layout,
      'dashboard dashboard-dark',
      { [s.layout__closedSidebar]: !sidebarOpened },
      { [s.layout__staticSidebar]: sidebarStatic }
    )}>
      <Sidebar />
      <div className={s.layout__container}>
        <Navbar />
        <div className={s.layout__contents}>
          {children}
        </div>
      </div>
    </div>
  );
};
