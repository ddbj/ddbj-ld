import { useEffect } from 'react';
import { signIn } from 'next-auth/react';

export default function LoginWithKeyCloak ({ callbackUrl = '/' }) {
  useEffect(() => {
    signIn('keycloak', { callbackUrl });
  }, [callbackUrl]);

  return null;
}
