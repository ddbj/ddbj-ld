import { API_BASE_URL } from '../../constants';


function createToken({ accessToken, expiresIn, expiresAt, refreshToken }) {
  return {
    accessToken,
    // NOTE: expiresAt あれば優先、なければ expiresIn で計算する
    accessTokenExpires: expiresAt * 10000 || Date.now() + expiresIn * 1000,
    refreshToken
  };
}

export async function getCurrentAccount(token) {
  const { accessToken } = token;
  const url = `${API_BASE_URL}/auth/account`;
  const headers = {
    'Authorization': `Bearer ${accessToken}`
  };
  const method = 'GET';
  const resposne = await fetch(url, { method, headers });
  const account = await resposne.json();

  return account;
}

export function createTokenFromAccount (account) {
  return createToken({
    accessToken : account.access_token,
    expiresAt   : account.expires_at,
    expiresIn   : account.refresh_expires_in,
    refreshToken: account.refresh_token
  });
}

export async function getRefreshedToken (token) {
  try {
    const { refreshToken } = token;

    const params = new URLSearchParams({ refresh_token: refreshToken });
    const url = `${API_BASE_URL}/auth/token?${params}`;
    const response = await fetch(url, { method: 'POST' });

    if (!response.ok) {
      throw new Error('RefreshAccessTokenError');
    }

    const data = await response.json();

    // NOTE: 新しい認証情報の生成
    const refreshedToken = createToken({
      accessToken : data.access_token,
      expiresAt   : data.expires_at,
      expiresIn   : data.refresh_expires_in,
      refreshToken: data.refresh_token ?? token.refreshToken
    });

    return refreshedToken;
  } catch (error) {
    return {
      ...token,
      error: error.message
    };
  }
}

export async function invalidateToken (token) {
  const { refreshToken } = token;
  const url = `${API_BASE_URL}/auth/${refreshToken}/logout`;
  const method = 'POST';
  await fetch(url, { method });
  return;
}
