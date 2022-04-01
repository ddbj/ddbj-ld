import { createElement } from 'react';
import classNames from 'classnames';

import * as s from './pages.module.scss';

export function Page ({ element = 'main', ...props }) {
  return createElement(
    element,
    {
      className: classNames('py-5 px-3 p-md-5', s.page),
      ...props
    }
  );
}

export function PageHeader ({ element='header', className = '', ...props }) {
  return createElement(
    element,
    {
      className: classNames(className, 'd-flex flex-row gap-3'),
      ...props
    }
  );
}

export function PageTitle ({ element = 'h1', ...props }) {
  return createElement(
    element,
    {
      ...props
    }
  );
}

export function PageSectionTitle ({ element = 'h2', ...props }) {
  return createElement(
    element,
    {
      ...props
    }
  );
}

export function PageSection ({ element = 'section', ...props }) {
  return createElement(
    element,
    {
      className: 'd-flex flex-column gap-3',
      ...props
    }
  );
}
