import {
  createApi,
  fetchBaseQuery
} from '@reduxjs/toolkit/query/react';

import { API_BASE_URL } from '@/constants';

import { CONTENT_TYPE } from '@/constants';

export function prepareHeaders (headers) {
  headers.set('Accept', 'application/json');
  if (headers.get('Content-Type') === CONTENT_TYPE.MULTI_PART_FORM) {
    // NOTE: boundary を自動で設定させるために削除する
    headers.delete('Content-Type');
  } else {
    headers.set('Content-Type', 'application/json');
  }
  return headers;
}

export const baseQuery = fetchBaseQuery({
  baseUrl: API_BASE_URL,
  prepareHeaders,
});

const appApi = createApi({
  reducerPath: 'app',
  baseQuery,
  endpoints  : () => ({})
});

export default appApi;
