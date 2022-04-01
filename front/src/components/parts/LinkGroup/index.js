import Link from 'next/link';
import classNames from 'classnames';
import { Collapse } from 'reactstrap';

import s from './LinkGroup.module.scss';

export default function LinkGroup ({
  icon, title, children,
  isOpened,
  isActive,
  onToggle
}) {
  return (
    <li className={classNames(s.linkGroup)}>
      <button className={classNames(s.linkGroup__header, { [s.linkGroup__header__active]: isActive })} onClick={onToggle}>
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

export function LinkItem ({ href, isActive, onClick, children, ...props }) {
  return (
    <li className={s.linkGroup__item} {...props}>
      <Link href={href} onClick={onClick} passHref>
        <a className={classNames(s.linkGroup__link, { [s.linkGroup__link__active]: isActive })}>
          {children}
        </a>
      </Link>
    </li>
  );
}

export function LinkItemButton({ onClick, isActive, children, ...props }) {
  return (
    <li className={s.linkGroup__item} {...props}>
      <button onClick={onClick} className={classNames(s.linkGroup__link, { [s.linkGroup__link__active]: isActive })}>
        {children}
      </button>
    </li>
  );
}
