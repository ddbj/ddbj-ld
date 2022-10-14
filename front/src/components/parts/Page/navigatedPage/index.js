import Link from 'next/link';
import classNames from 'classnames';
import { Children, useMemo } from 'react';

import {
  Row, Col,
  Nav as RSNav,
  NavLink as RSNavLink,
  NavItem as RSNavItem
} from 'reactstrap';

import PageSection,  { PageSrctionTitle } from '../PageSection';

import { useActive, Provider } from '@/components';

export function NavigatedPageContainer ({ children }) {
  const childrenArray = Children.toArray(children);

  const {
    hrefs,
    navigationItemList
  } = useMemo(() => {
    return childrenArray.reduce(({ hrefs, navigationItemList }, { props: { id, label } }) => {
      const href = `#${id}`;
      return ({
        hrefs             : [...hrefs, href],
        navigationItemList: [
          ...navigationItemList,
          {
            href,
            label
          }
        ]
      });

    }, {
      hrefs             : [],
      navigationItemList: []
    });
  }, [childrenArray]);

  return (
    <Provider hrefs={hrefs}>
      <Row>
        <Col xs={12} sm={12} md={4} lg={3} xl={2} className="mb-4">
          <RSNav className={classNames('py-2 flex-column')} pills>
            {navigationItemList.map(({ href, label }) => <PageNavigateItem key={href} href={href}>{label}</PageNavigateItem>)}
          </RSNav>
        </Col>
        <Col xs={12} sm={12} md={8} lg={9} xl={9} className="d-flex flex-column gap-5">
          {children}
        </Col>
      </Row>
    </Provider>
  );
}

function PageNavigateItem({ href, children, ...props }) {
  const active = useActive();
  return (
    <RSNavItem {...props}>
      <Link href={href} passHref>
        <RSNavLink active={active === href}>
          {children}
        </RSNavLink>
      </Link>
    </RSNavItem>
  );
}

export function NavigatedPageSection ({ label, id, children, ...props }) {
  return (
    <PageSection id={id} {...props}>
      <PageSrctionTitle>{label}</PageSrctionTitle>
      {children}
    </PageSection>
  );
}
