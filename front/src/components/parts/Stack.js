import { createElement, useMemo } from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';

export default function Stack ({
  className: defaultClassName = '',
  element = 'div',
  direction = 'row',
  alignItems,
  justifyContent,
  gap,
  ...props
}) {
  const className = useMemo(() => classNames(
    'd-flex',
    ...(typeof direction === 'string' ? ([
      `flex-${direction}`
    ]) : (
      Object.entries(direction).map(([size, value]) => size === 'xs' ? `flex-${value}` : `flex-${size}-${value}`)
    )),
    ...(justifyContent ? (typeof justifyContent === 'string' ? ([
      `justify-content-${justifyContent}`
    ]) : (
      Object.entries(justifyContent).map(([size, value]) => size === 'xs' ? `justify-content-${value}` : `justify-content-${size}-${value}`)
    )) : []),
    ...(alignItems ? (typeof alignItems === 'string' ? ([
      `align-items-${alignItems}`
    ]) : (
      Object.entries(alignItems).map(([size, value]) => size === 'xs' ? `align-items-${value}` : `align-items-${size}-${value}`)
    )) : []),
    ...(gap ? (typeof gap === 'string' || typeof gap === 'number' ? ([
      `gap-${gap}`
    ]) : (
      Object.entries(gap).map(([size, value]) => size === 'xs' ? `gap-${value}` : `gap-${size}-${value}`)
    )) : []),
    defaultClassName
  ), [direction, justifyContent, alignItems, gap, defaultClassName]);

  return createElement(element, { className, ...props });
}

const directionValues = Object.freeze([
  'row',
  'row-reverse',
  'column',
  'column-reverse',
]);

const justifyContentValues = Object.freeze([
  'start',
  'end',
  'center',
  'between',
  'around',
  'evenly',
]);

const alignItemsValues = Object.freeze([
  'start',
  'end',
  'center',
  'baseline',
  'stretch',
]);

const gapValues = Object.freeze([
  1,
  2,
  3,
  4,
  5
]);

Stack.propTyps = {
  element: PropTypes.oneOf([
    PropTypes.element,
    PropTypes.elementType
  ]),
  className: PropTypes.string,
  direction: PropTypes.oneOf([
    PropTypes.oneOf(directionValues),
    PropTypes.shape({
      xs : PropTypes.oneOf(directionValues),
      sm : PropTypes.oneOf(directionValues),
      md : PropTypes.oneOf(directionValues),
      lg : PropTypes.oneOf(directionValues),
      xl : PropTypes.oneOf(directionValues),
      xxl: PropTypes.oneOf(directionValues),
    })
  ]),
  alignItems: PropTypes.oneOf([
    PropTypes.oneOf(alignItemsValues),
    PropTypes.shape({
      xs : PropTypes.oneOf(alignItemsValues),
      sm : PropTypes.oneOf(alignItemsValues),
      md : PropTypes.oneOf(alignItemsValues),
      lg : PropTypes.oneOf(alignItemsValues),
      xl : PropTypes.oneOf(alignItemsValues),
      xxl: PropTypes.oneOf(alignItemsValues),
    })
  ]),
  justifyContent: PropTypes.oneOf([
    PropTypes.oneOf(justifyContentValues),
    PropTypes.shape({
      xs : PropTypes.oneOf(justifyContentValues),
      sm : PropTypes.oneOf(justifyContentValues),
      md : PropTypes.oneOf(justifyContentValues),
      lg : PropTypes.oneOf(justifyContentValues),
      xl : PropTypes.oneOf(justifyContentValues),
      xxl: PropTypes.oneOf(justifyContentValues),
    })
  ]),
  gap: PropTypes.oneOf([
    PropTypes.oneOf(gapValues),
    PropTypes.shape({
      xs : PropTypes.oneOf(gapValues),
      sm : PropTypes.oneOf(gapValues),
      md : PropTypes.oneOf(gapValues),
      lg : PropTypes.oneOf(gapValues),
      xl : PropTypes.oneOf(gapValues),
      xxl: PropTypes.oneOf(gapValues),
    })
  ])
};
