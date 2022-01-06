import snakecaseKeys from 'snakecase-keys';
import camelcaseKeys from 'camelcase-keys';

import appApi from './appApi';

export function transformGrantForApi (appGrant) {
  return snakecaseKeys(appGrant, { deep: true });
}

export function transformGrantForApp (apiGrant) {
  return camelcaseKeys(apiGrant, { deep: true });
}

const grantApi = appApi.injectEndpoints({
  endpoints: build => ({
    createProjectGrant: build.mutation({
      query: ({ grant: appGrant }) => ({
        url   : '/view/access-grant/project',
        method: 'POST',
        body  : transformGrantForApi(appGrant)
      })
    }),
    getProjectGrantList: build.query({
      query: () => ({
        url: '/view/access-grant/project',
      }),
      transformResponse: apiGrantList => apiGrantList.map(apiGrant => transformGrantForApp(apiGrant))
    }),
    deleteGrantToken: build.mutation({
      query: ({ token }) => ({
        url   : `/view/access-grant/token/${token}`,
        method: 'DELETE'
      })
    }),
    // TODO: 仕様確認
    updateGrantToken: build.mutation({
      query: ({ token, grant: appGrant }) => ({
        url   : `/view/access-grant/token/${token}`,
        method: 'UPDATE',
        body  : transformGrantForApi(appGrant)
      })
    }),
    getGrantTokenPreview: build.query({
      query: ({ token }) => ({
        url: `/view/access-grant/token/${token}/preview`,
      })
    }),
  })
});

export const {

} = grantApi;
