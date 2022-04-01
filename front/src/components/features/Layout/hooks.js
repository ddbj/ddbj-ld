import { useEffect } from 'react';
import { useCallback } from 'react';
import { useSelector, useDispatch } from 'react-redux';

import {
  selectNavigationState,
  openSideBar,
  closeSideBar,
  toggleSideBar,
  openSidebarGroup,
  toggleSidebarGroup
} from '../../../slices/navigationSlice';

export function useSideBar (currentPathname) {
  const dispatch = useDispatch();

  const {
    isSideBarOpened,
    isSideBarStatic,
    openedSideBarGroupName,
  } = useSelector(selectNavigationState);

  const open = useCallback(({ isSideBarStatic = false }) => {
    dispatch(
      openSideBar({ isSideBarStatic })
    );
  }, [dispatch]);

  const close = useCallback(() => {
    dispatch(
      closeSideBar()
    );
  }, [dispatch]);

  const toggle = useCallback(() => {
    dispatch(
      toggleSideBar()
    );
  }, [dispatch]);

  const openGroup = useCallback(sideBarGroupName => {
    dispatch(
      openSidebarGroup({ sideBarGroupName })
    );
  }, [dispatch]);

  const toggleGroup = useCallback(sideBarGroupName => {
    dispatch(
      toggleSidebarGroup({ sideBarGroupName })
    );
  }, [dispatch]);

  useEffect(() => {
    openGroup(currentPathname);
  }, [currentPathname, openGroup]);

  return {
    isOpened       : isSideBarOpened,
    isStatic       : isSideBarStatic,
    toggle,
    open,
    close,
    openedGroupName: openedSideBarGroupName,
    openGroup,
    toggleGroup
  };
}
