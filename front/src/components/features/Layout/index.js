import { useState } from 'react';
import { LOCALE } from '@/constants';
import { useRouter } from 'next/router';
import { FormattedMessage } from 'react-intl';
import { Navbar, Dropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';

export default function Layout ({ children }){
  const router = useRouter();
  const [isDropdownOpen, setIsDropdown] = useState(false)

  return (
    <>
      <Navbar>
        <div className="flex-grow-1" />
        <Dropdown isOpen={isDropdownOpen} toggle={() => setIsDropdown(isOpend => !isOpend)}>
          <DropdownToggle color='light' caret>
            <i className="bi bi-translate me-2" />
            {router.locale === LOCALE.JA ? (
              <FormattedMessage id="language.japanese" />
            ) : (
              <FormattedMessage id="language.english" />
            )}
          </DropdownToggle>
          <DropdownMenu>
            <DropdownItem onClick={() => router.replace(router.asPath, undefined, { locale: LOCALE.JA })} active={router.locale === LOCALE.JA}>
              <FormattedMessage id="language.japanese" />
            </DropdownItem>
            <DropdownItem onClick={() => router.replace(router.asPath, undefined, { locale: LOCALE.EN })} active={router.locale === LOCALE.EN}>
              <FormattedMessage id="language.english" />
            </DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </Navbar>
      {children}
    </>
  );
};
