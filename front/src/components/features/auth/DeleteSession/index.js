import { useEffect } from 'react';
import { useSignOutWithInvalidatingToken } from '../../../../hooks/auth';

export default function DeleteSession ({ callbackUrl = '/'}) {
  const signOutWithInvalidatingToken = useSignOutWithInvalidatingToken();

  useEffect(() => {
    if (!signOutWithInvalidatingToken) return;
    signOutWithInvalidatingToken({ callbackUrl });
  }, [signOutWithInvalidatingToken, callbackUrl]);

  return null;
}
