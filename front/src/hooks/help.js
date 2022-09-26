import { useMemo } from 'react';
import { HELP_SITE_URL } from '../constants';

export function useHelpPage (path = '/') {
  return useMemo(() => `${HELP_SITE_URL}${path}`, [path]);
}

// NOTE: 表示言語によるロジックを切り分けた
export function useJvarHelpPageUrl () {
  return useHelpPage('/jvar/index-e.html');
}

// NOTE: 表示言語によるロジックを切り分けた
export function useBioSampleHelpPageUrl () {
  return useHelpPage('/biosample/index-e.html');
}
