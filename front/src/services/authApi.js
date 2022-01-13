import { idpUrl, clientUrl } from '../config';

import request from './request';

export const authorizeUrl = `${idpUrl}/openam/oauth2/authorize`;
export const redirectUri = `${clientUrl}/authorize`;

// HACK: 適切な方法がわからない
export async function login (code) {
  return await request({
    url   : `/auth/${code}/login`,
    method: 'POST'
  });
}

// HACK: 適切な方法がわからない
export async function logout () {
  return await request({
    url   : '/auth/logout',
    method: 'POST'
  });
};
