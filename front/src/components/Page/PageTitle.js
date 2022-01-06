import classNames from 'classnames';
import { createElement } from 'react';

import * as s from './Page.module.scss';

export default function PageTitle ({ className = '', element = 'h1', children, ...props }) {
  return createElement(element, {
    className: classNames(className, s.page__title),
    ...props
  }, children);
}
