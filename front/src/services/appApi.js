import {
  createApi,
  fetchBaseQuery
} from '@reduxjs/toolkit/query/react';

import { apiBaseUrl } from '../config';
import { selectAuthState, setAccessToken,　logout, selectCurrentUser  } from '../slices/authSlice';

import { CONTENT_TYPE } from '../constants';

export function prepareHeaders (headers, api) {
  headers.set('Accept', 'application/json');

  const state = api.getState();
  const { accessToken } = selectAuthState(state);
  if (accessToken) headers.set('Authorization', `Bearer ${accessToken}`);

  if (headers.get('Content-Type') === CONTENT_TYPE.MULTI_PART_FORM) {
    // NOTE: boundary を自動で設定させるために削除する
    headers.delete('Content-Type');
  } else {
    headers.set('Content-Type', 'application/json');
  }

  return headers;
}

export const baseQuery = fetchBaseQuery({
  baseUrl: apiBaseUrl,
  prepareHeaders,
});

export function createBaseQueryWithReauth (baseQuery) {
  return async function baseQueryWithReauth (args, api, extraOptions) {
    let result = await baseQuery(args, api, extraOptions);
    if (result.error && result.error.status === 401) {
      const currentUser = selectCurrentUser(api.getState());
      if (currentUser) {
        const refreshResult = await baseQuery(`/auth/${currentUser.uuid}/token`, api, extraOptions);
        if (refreshResult.data) {
          api.dispatch(setAccessToken(refreshResult.data));
          result = await baseQuery(args, api, extraOptions);
        }
      }
    }
    return result;
  };
}

export const baseQueryWithReauth = createBaseQueryWithReauth(baseQuery);

const appApi = createApi({
  reducerPath: 'app',
  baseQuery  : baseQueryWithReauth,
  endpoints  : () => ({})
});

export default appApi;
