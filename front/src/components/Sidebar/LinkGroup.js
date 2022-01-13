import { useCallback, useMemo } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/router';
import classNames from 'classnames';
import { useDispatch } from 'react-redux';

import { Collapse } from 'reactstrap';

import s from './LinkGroup.module.scss';
import { useSelector } from 'react-redux';
import { selectNavigationState, setOpenedGroup } from '../../slices/navigationSlice';
import { useCloseSidebar } from '../../hooks/navigation';

function isMatchedPathname (basePathname, currentPathname, exact = false) {
  return exact ? basePathname === currentPathname : new RegExp(`^${basePathname}`).test(currentPathname);
}

export default function LinkGroup ({
  title, pathname, exact = false, icon, children
}) {
  const { pathname: currentPathName } = useRouter();

  const dispatch = useDispatch();
  const { openedGroup, sidebarOpened } = useSelector(selectNavigationState);

  const isActive = useMemo(() => isMatchedPathname(pathname, currentPathName, exact), [currentPathName, exact, pathname]);
  const isOpened = useMemo(() => sidebarOpened && isMatchedPathname(pathname, openedGroup, exact), [sidebarOpened, exact, openedGroup, pathname]);

  const toggleGroup = useCallback(() => {
    dispatch(setOpenedGroup(isOpened ? null : pathname));
  }, [dispatch, isOpened, pathname]);

  return (
    <li className={classNames(s.linkGroup)}>
      <button className={classNames(s.linkGroup__header, { [s.linkGroup__header__active]: isActive })} onClick={toggleGroup}>
        {icon && (
          <span className={classNames(s.linkGroup__icon, s.icon)}>
            <i className={classNames('fi', icon)}/>
          </span>
        )}
        <span className={s.linkGroup__title}>{title}</span>
        <i className={classNames('fa', 'fa-angle-left', s.linkGroup__caret, { [s.linkGroup__caret__opened]: isOpened })} />
      </button>
      {children && (
        <Collapse isOpen={isOpened}>
          <ul className={s.linkGroup__items}>
            {children}
          </ul>
        </Collapse>
      )}
    </li>
  );
}

export function LinkItem ({ href, exact = false, children, ...props }) {
  const { pathname } = useRouter();
  const isActive = useMemo(() => isMatchedPathname(href, pathname, exact), [exact, href, pathname]);
  const closeSidebar = useCloseSidebar(true);

  return (
    <li className={s.linkGroup__item} {...props}>
      <Link href={href} onClick={closeSidebar} passHref>
        <a className={classNames(s.linkGroup__link, { [s.linkGroup__link__active]: isActive })}>{children}</a>
      </Link>
    </li>
  );
}

export function LinkItemButton({ onClick, children, ...props }) {
  const closeSidebar = useCloseSidebar(true);

  const handleClick = useCallback((event) => {
    closeSidebar();
    onClick(event);
  }, [closeSidebar, onClick]);
  return (
    <li className={s.linkGroup__item} {...props}>
      <button onClick={handleClick} className={classNames(s.linkGroup__link)}>{children}</button>
    </li>
  );
}
