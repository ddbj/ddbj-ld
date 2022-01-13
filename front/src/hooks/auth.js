import { useCallback, useMemo } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { idpClientId, idpScope } from '../config';
import { redirectUri, authorizeUrl } from '../services/authApi';
import { selectAuthState } from '../slices/authSlice';

export function useAuthrizeUrl () {
  return useMemo(() => {
    const url = new URL(authorizeUrl);

    url.searchParams.set('response_type', 'code');
    url.searchParams.set('client_id', idpClientId);
    url.searchParams.set('scope', idpScope);
    url.searchParams.set('redirect_uri', redirectUri);

    return url;
  }, []);
}

export function useLogout () {
  const dispatch = useDispatch();

  return useCallback(() => {
    dispatch(logout());
  }, [dispatch]);
}

export function useIsAuthorized () {
  const authState = useSelector(selectAuthState);
  return Boolean(authState?.accessToken);
}

export function useIsAdmin () {
  const authState = useSelector(selectAuthState);
  return Boolean(authState?.currentUser?.admin);
}

export function useCurrentUser () {
  const authState = useSelector(selectAuthState);
  return authState?.currentUser || null;
}
