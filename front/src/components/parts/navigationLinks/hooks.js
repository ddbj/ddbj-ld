import { createContext, useContext, useEffect } from 'react';

import { useToggleState } from '@/hooks/ui';

const Context = createContext();

export function Provider ({ router, ...props }) {
  return <Context.Provider value={{ router }} {...props} />;
}

export function useRouter() {
  const { router } = useContext(Context);
  return router;
}

export function useNavigationLink ({ href, exact }) {
  const router = useRouter();
  const [isActive, , setIsActive] = useToggleState(false);

  useEffect(() => {
    const regExp = new RegExp(`^${href}`);
    const isActive = exact ? href === router.pathname : regExp.test(router.pathname);
    setIsActive(isActive);
  }, [exact, href, router.pathname, setIsActive]);

  return { isActive };
}

export function useNavigationLinkGroup ({ pathname, exact = false }) {
  const router = useRouter();
  const [isOpened, toggleOpened, setIsOpened] = useToggleState(false);
  const [isActive, , setIsActive] = useToggleState(false);

  useEffect(() => {
    const regExp = new RegExp(`^${pathname}`);
    const isActive = exact ? pathname === router.pathname : regExp.test(router.pathname);
    setIsActive(isActive);
    if (!isActive) return;
    setIsOpened(true);
  }, [exact, pathname, router.pathname, setIsActive, setIsOpened]);

  return {
    isOpened,
    isActive,
    toggle: toggleOpened
  };
}
