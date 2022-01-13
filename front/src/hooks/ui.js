import { useCallback, useState } from 'react';

export function useToggleState (defaultFlag = false) {
  const [flag, setFlag] = useState(defaultFlag);
  const toggle = useCallback(() => setFlag(flag => !flag), []);
  return [flag, toggle, setFlag];
}

export function useModal (defaultIsOpened = false) {
  const [isOpened, toggle, setIsOpened] = useToggleState(defaultIsOpened);
  return [isOpened, toggle, setIsOpened];
}

export function useDropdown (defaultIsOpened = false) {
  const [isOpened, toggle, setIsOpened] = useToggleState(defaultIsOpened);
  return [isOpened, toggle, setIsOpened];
}
