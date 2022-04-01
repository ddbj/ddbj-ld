import { useRouter } from 'next/router';
import classNames from 'classnames';

import { useCurrentUser } from '../../../hooks/auth';
import { useSideBar } from './hooks';

import Navbar from '../../parts/Navbar';
import Sidebar from '../../parts/Sidebar';

import s from './Layout.module.scss';

export default function Layout ({ children }){
  const router = useRouter();
  const currentUser = useCurrentUser();

  const {
    // SideBar
    isOpened: isSideBarOpened,
    isStatic: isSideBarStatic,
    open: openSideBar,
    close: closeSideBar,
    toggle: toggleSideBar,
    // SideBarGroup
    openedGroupName: openedSideBarGroupName,
    toggleGroup: toggleSidebarGroup
  } = useSideBar(
    router.pathname
  );

  return (
    <div className={classNames(
      s.layout,
      'dashboard dashboard-dark',
      {
        [s.layout__closedSidebar]: !isSideBarOpened,
        [s.layout__staticSidebar]: isSideBarStatic
      }
    )}>
      <Sidebar
        currentUser={currentUser}
        router={router}
        // SideBar
        isOpened={isSideBarOpened}
        isStatic={isSideBarStatic}
        onOpen={openSideBar}
        onClose={closeSideBar}
        onToggle={toggleSideBar}
        // SideBarGroup
        openedGroupName={openedSideBarGroupName}
        onToggleGroup={toggleSidebarGroup}
      />
      <div className={s.layout__container}>
        <Navbar onToggleSideBar={toggleSideBar} />
        <div className={s.layout__contents}>
          {children}
        </div>
      </div>
    </div>
  );
};
