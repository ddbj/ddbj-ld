import { signIn, useSession } from 'next-auth/react';
import { useEffect } from 'react';

export default function RefreshToken () {
  const { data:session } = useSession();

  useEffect(() => {
    if (session?.token?.error !== 'RefreshAccessTokenError') return;
    signIn('keycloak');
  }, [session]);

  return null;
}
