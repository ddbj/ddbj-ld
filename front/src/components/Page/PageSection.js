import { createElement } from 'react';
import classNames from 'classnames';

import * as s from './Page.module.scss';

export default function PageSection ({ className = '', ...props }) {
  return <section className={classNames('d-flex flex-column gap-2', className)} {...props} />;
}

export function PageSrctionTitle ({ className = '', element = 'h2', children, ...props }) {
  return createElement(element, {
    className: classNames(className, s.page__section_title),
    ...props
  }, children);
}
