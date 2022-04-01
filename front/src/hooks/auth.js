import { useMemo } from 'react';
import { useSession, signOut } from 'next-auth/react';

import { invalidateToken } from '../services/nextauth';

export function useCurrentUser () {
  const { data, status } = useSession();

  // TODO: 開発用の機能ため削除
  if (status === "authenticated" && !data?.user) {
    return {
      uid: "test"
    }
  }

  return data?.user || null;
}

export function useSignOutWithInvalidatingToken () {
  const { data: session } = useSession();
  return useMemo(() => {
    if (!session?.token) return null;

    return async function signOutWithInvalidatingToken () {
      await invalidateToken(session.token);
      signOut(...arguments);
    };
  }, [session?.token]);
}
