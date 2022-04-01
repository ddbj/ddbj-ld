import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import { getSession } from 'next-auth/react';

import { CONTENT_TYPE, API_BASE_URL } from '../constants';

export async function prepareHeaders (headers) {
  headers.set('Accept', 'application/json');

  const contentType = headers.get('Content-Type');
  if (contentType === CONTENT_TYPE.MULTI_PART_FORM) {
    headers.delete('Content-Type');
  } else {
    headers.set('Content-Type', 'application/json');
  }

  const data = await getSession();
  const isAuthenticated = !!data;
  if (isAuthenticated) {
    const { accessToken } = data.token;
    headers.set('Authorization', `Bearer ${accessToken}`);
  }

  return headers;
}

export const baseQuery = fetchBaseQuery({
  baseUrl: API_BASE_URL,
  prepareHeaders,
});

const appApi = createApi({
  reducerPath: 'app',
  baseQuery  : baseQuery,
  endpoints  : () => ({})
});

export default appApi;
