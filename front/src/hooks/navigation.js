import { useRouter } from 'next/router';
import { useCallback, useEffect, useMemo } from 'react';
import { useSelector, useDispatch } from 'react-redux';

import {
  setActiveItem,
  setSidebarOpened,
  setSidebarStatic,
  selectNavigationState
} from '../slices/navigationSlice';

export function useResponsiveSidebar () {
  const { sidebarStatic } = useSelector(selectNavigationState);

  const openSidebar = useCloseSidebar(false);
  const closeSidebar = useCloseSidebar();

  // サイドバーが固定されていない状態で、読み込まれた時に画面幅に応じてサイドバーを開く
  useEffect(() => {
    if (sidebarStatic || window.innerWidth <= 768) return;
    openSidebar();
  }, [openSidebar, sidebarStatic]);

  useEffect(() => {
    function handleResize () {
      if (sidebarStatic || window.innerWidth > 768) return;
      closeSidebar();
    }

    handleResize();

    window.addEventListener('resize', handleResize);
    return function cleanup () {
      window.removeEventListener('resize', handleResize);
    };
  }, [closeSidebar, sidebarStatic]);
}

export function useToggleSidebar (withStatic = false) {
  const { sidebarOpened } = useSelector(selectNavigationState);

  const openSidebar = useOpenSidebar(withStatic);
  const closeSidebar = useCloseSidebar(withStatic);

  return useCallback(() => {
    sidebarOpened ? closeSidebar() : openSidebar();
  }, [sidebarOpened, openSidebar, closeSidebar]);
}

export function useOpenSidebar (withStatic = false) {
  const dispatch = useDispatch();
  const { sidebarStatic } = useSelector(selectNavigationState);
  return useCallback(() => {
    dispatch(setSidebarOpened(true));
    dispatch(setSidebarStatic(sidebarStatic || withStatic));
  }, [dispatch, withStatic, sidebarStatic]);
}

export function useCloseSidebar (withStatic = false) {
  const dispatch = useDispatch();
  const { sidebarStatic } = useSelector(selectNavigationState);
  return useCallback(() => {
    if (!withStatic && sidebarStatic) return;
    dispatch(setSidebarOpened(false));
    dispatch(setSidebarStatic(false));
  }, [withStatic, dispatch, sidebarStatic]);
}

export function useIsCurrentItem (item, exact = false) {
  const { pathname } = useRouter();
  return useMemo(() => {
    if (exact) return pathname === item;
    return pathname.match(item);
  }, [exact, item, pathname]);
}

export function useSetActiveItem () {
  const dispatch = useDispatch();

  return useCallback((item) => {
    dispatch(setActiveItem(item));
  }, [dispatch]);
}

export function useIsActiveItem (item, exact = false) {
  const sidebarOpened = useSidebarOpened();
  const activeItem = useActiveItem();

  return useMemo(() => {
    if (!sidebarOpened || !activeItem) return false;
    if (exact) return activeItem === item;
    return !!activeItem.match(item);
  }, [sidebarOpened, activeItem, exact, item]);
}
