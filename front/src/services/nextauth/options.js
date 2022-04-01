import KeycloakProvider from 'next-auth/providers/keycloak';

import {
  createTokenFromAccount,
  getRefreshedToken,
  // getCurrentAccount,
} from '.';

export const nextAuthOptions = {
  secret   : process.env.NEXTAUTH_SECRET,
  providers: [
    KeycloakProvider({
      clientId     : process.env.KEYCLOAK_CLIENT_ID,
      clientSecret : process.env.KEYCLOAK_CLIENT_SECRET,
      issuer       : process.env.KEYCLOAK_ISSUER,
      authorization: {
        params: {
          scope: process.env.KEYCLOAK_SCOPE
        }
      }
    })
  ],
  callbacks: {
    async session({ session, user, token  }) {
      try {
        return { ...session, token, user };
      } catch (error) {
        throw error;
      }
    },
    async jwt({ token, account }) {
      // NOTE: 初回認証時
      if (account) {
        const token = createTokenFromAccount(account);
        return token;
      }

      // NOTE:トークンの有効期限内
      if (Date.now() <= token.accessTokenExpires) {
        return token;
      }

      // NOTE:トークンの更新を試みる
      const refreshedToken = await getRefreshedToken(token);
      return refreshedToken;
    },
  }
};
