import Link from 'next/link';
import {
  Breadcrumb as RSBreadcrumb,
  BreadcrumbItem
} from 'reactstrap';

import * as s from './Breadcrumb.module.scss';

export default function Breadcrumbs ({ breadcrumbs }) {
  return (
    <RSBreadcrumb className={s.breadcrumbs}>
      {breadcrumbs.map(({ label, href }, index, breadcrumb) => {

        const content = href ? (
          <a>{label}</a>
        ) : (
          <span>{label}</span>
        );

        return (
          <BreadcrumbItem key={index} active={index === (breadcrumb.length - 1)}>
            {href ? (
              <Link href={href} passHref>
                {content}
              </Link>
            ) : (
              content
            )}
          </BreadcrumbItem>
        );
      })}
    </RSBreadcrumb>
  );
}
