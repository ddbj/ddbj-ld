import classNames from 'classnames';

import * as s from './TopBar.module.scss';

export default function TopBar ({ toggleSideBar }) {
  return (
    <div className={classNames(s.TopBar, 'd-lg-none')}>
      <button className={s.TopBar__ToggleButton} onClick={toggleSideBar}>
        <i className={classNames(s.TopBar__ToggleButtonIcon, 'bi-list')} />
      </button>
    </div>
  );
}
