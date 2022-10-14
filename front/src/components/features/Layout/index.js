import { useEffect } from 'react';
import { useRouter } from 'next/router';
import classNames from 'classnames';

import { useToggleState } from '@/hooks/ui';
import TopBar from '@/components/parts/TopBar';
import SideBar from '@/components/parts/SideBar';

import * as s from './Layout.module.scss';

export default function Layout ({ children }){
  const router = useRouter();
  const [isOpened, toggle, setState] = useToggleState(false);

  // NOTE: ページ遷移したらサイドバーを閉じる
  useEffect(() => {
    setState(false);
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [router.pathname]);

  return (
    <>
      <TopBar toggleSideBar={toggle} />
      <div className={classNames('d-flex flex-row')}>
        <div className={s.Layout__SideBarContainer}>
          <SideBar isOpened={isOpened} toggle={toggle} />
        </div>
        <div className={classNames('flex-grow-1')}>
          {children}
        </div>
      </div>
    </>
  );
};
