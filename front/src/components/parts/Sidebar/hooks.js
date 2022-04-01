import { useCallback } from 'react';

export function useGetLinkGroupProps ({
  isSideBarOpened = false,
  openedGroupName = null,
  onToggleGroup
}) {
  return useCallback(({ pathname, exact = false, ...props } = {}) => {
    const isOpened = isSideBarOpened && isMatchedPathname(pathname, openedGroupName, exact);
    const isActive = isMatchedPathname(pathname, openedGroupName, exact);

    const toggle = () => onToggleGroup(pathname);

    return {
      isOpened,
      isActive,
      onToggle: toggle,
      ...props
    };
  }, [openedGroupName, isSideBarOpened, onToggleGroup]);
}

export function useGetLinkItemProps ({
  isSideBarOpened = false,
  currentPathname = '/',
  closeSideBar
}) {
  return useCallback(({ href, exact = false, ...props } = {}) => {
    const isActive = isSideBarOpened && isMatchedPathname(href, currentPathname, exact);

    const close = () => closeSideBar();

    return {
      href,
      isActive,
      onClick: close,
      ...props
    };
  }, [closeSideBar, currentPathname, isSideBarOpened]);
}

function isMatchedPathname (basePathname, currentPathname, exact = false) {
  return exact ? basePathname === currentPathname : new RegExp(`^${basePathname}`).test(currentPathname);
}
