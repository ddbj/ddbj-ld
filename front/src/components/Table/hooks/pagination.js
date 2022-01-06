import { useMemo } from 'react';

export const DOTS = '...';
export const DEFAULT_DELTA = 2;

export const COUNT_OPTIONS = [
  10,
  20,
  30,
  50,
  100,
];

export const DEFAULT_OFFSET = 0;

export const DEFAULT_COUNT = 20;

export function usePagesWithDots(current, size, delta = DEFAULT_DELTA, dots = DOTS) {
  return useMemo(() => {
    const left = current - delta,
      right = current + delta + 1,
      pages = [],
      pagesWithDots = [];

    for (let i = 1; i <= size; i++) {
      if (i == 1 || i == size || i >= left && i < right) {
        pages.push(i);
      }
    }

    let l = null;
    for (let i of pages) {
      if (l) {
        if (i - l === 2) {
          pagesWithDots.push(l + 1);
        } else if (i - l !== 1) {
          pagesWithDots.push(dots);
        }
      }
      pagesWithDots.push(i);
      l = i;
    }

    return pagesWithDots;
  }, [current, size, delta, dots]);
}
