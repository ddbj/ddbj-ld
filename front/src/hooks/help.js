import { useMemo } from 'react';
import { helpSiteUrl } from '../config';

export function useHelpPage (path = '/') {
  return useMemo(() => `${helpSiteUrl}${path}`, [path]);
}

// NOTE: 表示言語によるロジックを切り分けた
export function useJvarHelpPageUrl () {
  return useHelpPage('/jvar/index-e.html');
}

// NOTE: 表示言語によるロジックを切り分けた
export function useBioSampleHelpPageUrl () {
  return useHelpPage('/biosample/index-e.html');
}
