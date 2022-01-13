import snakecaseKeys from 'snakecase-keys';
import camelcaseKeys from 'camelcase-keys';
import FileSaver from 'file-saver';

import { CONTENT_TYPE, ENTRY_REQUEST_TYPE } from '../constants';

import appApi, { baseQuery } from './appApi';

export function transformEntryForApi (appEntry) {
  return snakecaseKeys(appEntry, { deep: true });
}

import { v4 as v4uuid } from 'uuid';
export function transformEntryForApp (apiEntry) {

  const appEntry = camelcaseKeys(apiEntry, { deep: true });

  console.warn('強制的にテスト用のrequestsを挿入しています');
  appEntry.requests = [{
    uuid   : v4uuid(),
    author : 'test',
    type   : ENTRY_REQUEST_TYPE.CANCEL,
    comment: 'テスト用のリクエスト'
  }, {
    uuid   : v4uuid(),
    author : 'test',
    type   : ENTRY_REQUEST_TYPE.PUBLISH,
    comment: 'テスト用のリクエスト'
  }, {
    uuid   : v4uuid(),
    author : 'test',
    type   : ENTRY_REQUEST_TYPE.UPDATE,
    comment: 'テスト用のリクエスト'
  }];

  return appEntry;
  // return camelcaseKeys(apiEntry, { deep: true });
}

export function transformEntryCommentForApp (apiEntryComment) {
  return snakecaseKeys(apiEntryComment, { deep: true });
}

export function transformEntryCommentForApi (appEntryComment) {
  return camelcaseKeys(appEntryComment, { deep: true });
}

export function transformEntryRequestForApp (apiEntryRequest) {
  return snakecaseKeys(apiEntryRequest, { deep: true });
}

export function transformEntryRequestForApi (appEntryRequest) {
  return camelcaseKeys(appEntryRequest, { deep: true });
}

function createGetEntryFileUrl (entryUuid, fileType, fileName) {
  return `/entry/${entryUuid}/file/${fileType}/${fileName}`;
}

const entryApi = appApi.injectEndpoints({
  endpoints: build => ({
    getEntryList: build.query({
      query            : () => '/entry',
      transformResponse: (apiEntryList) => apiEntryList.map(apiEntry => transformEntryForApp(apiEntry))
    }),
    getEntry: build.query({
      query            : ({ entryUuid }) => `/entry/${entryUuid}`,
      transformResponse: (apiEntry) => transformEntryForApp(apiEntry)
    }),
    checkUpdateToken: build.query({
      query: ({ entryUuid, updateToken }) => `/entry/${entryUuid}/update_token/${updateToken}/check`
    }),
    createEntry: build.mutation({
      query: ({ entry: appEntry }) => ({
        url   : '/entry',
        method: 'POST',
        body  : transformEntryForApi(appEntry)
      })
    }),
    updateEntry: build.mutation({
      query: ({ entryUuid, entry: appEntry }) => ({
        url   : `/entry/${entryUuid}`,
        method: 'PUT',
        body  : transformEntryForApi(appEntry)
      })
    }),
    deleteEntry: build.mutation({
      query: ({ entryUuid }) => ({
        url   : `/entry/${entryUuid}`,
        method: 'DELETE'
      })
    }),
    validateEntry: build.mutation({
      query: ({ entryUuid }) => ({
        url   : `/entry/${entryUuid}/validate`,
        method: 'POST'
      })
    }),
    submitEntry: build.mutation({
      query: ({ entryUuid }) => ({
        url   : `/entry/${entryUuid}/submit`,
        method: 'POST'
      })
    }),
    createEntryComment: build.mutation({
      query: ({ entryUuid, entryComment: appEntryComment }) => ({
        url   : `/entry/${entryUuid}/comment`,
        method: 'POST',
        body  : transformEntryCommentForApi(appEntryComment)
      })
    }),
    updateEntryComment: build.mutation({
      query: ({ entryUuid, entryCommentUuid, entryComment: appEntryComment }) => ({
        url   : `/entry/${entryUuid}/comment/${entryCommentUuid}`,
        method: 'POST', // TODO: PUTではないのか確認
        body  : transformEntryCommentForApi(appEntryComment)
      })
    }),
    deleteEntryComment: build.mutation({
      query: ({ entryUuid, entryCommentUuid }) => ({
        url   : `/entry/${entryUuid}/comment/${entryCommentUuid}`,
        method: 'DELETE'
      })
    }),
    getEntryFile: build.query({
      query: ({ entryUuid, fileType, fileName }) => ({
        url: createGetEntryFileUrl(entryUuid, fileType, fileName)
      })
    }),
    // REVIEW: query / mutation のどちらが最適か。そもそも、RTK Query 内でいいのか
    // NOTE: ダウンロードまで同スレッドで行うため
    downlaodEntryFile: build.mutation({
      queryFn: async ({ entryUuid, fileType, fileName }, _api, _extra, baseQuery) => {
        const {
          data,
          meta: {
            response
          }
        } = await baseQuery({
          url: createGetEntryFileUrl(entryUuid, fileType, fileName)
        });

        if (!response.ok) {
          return {
            error: {
              status : response.status,
              message: response.statusText
            }
          };
        }

        FileSaver.saveAs(data, fileName);

        return { data: null };
      }
    }),
    uploadEntryFile: build.mutation({
      queryFn: async ({ entryUuid, fileType, fileName, file }, _api, _extra, baseQuery) => {
        try {
          const {
            data: {
              token: entryFileUploadToken
            }
          } = await baseQuery({
            url: `/entry/${entryUuid}/file/${fileType}/${fileName}/pre_upload`
          });

          const body = new FormData();
          body.append('file', file, fileName);

          await baseQuery({
            url    : `/entry/${entryUuid}/file/${fileType}/${fileName}/${entryFileUploadToken}/upload`,
            method : 'POST',
            headers: {
              'Content-Type': CONTENT_TYPE.MULTI_PART_FORM
            },
            body
          });
          return { data: null };
        } catch (error) {
          return { error };
        }
      }
    }),
    deleteEntryFile: build.mutation({
      query: ({ entryUuid, fileType, fileName }) => ({
        url   : `/entry/${entryUuid}/file/${fileType}/${fileName}`,
        method: 'DELETE'
      })
    }),
    createEntryRequest: build.mutation({
      query: ({ entryUuid, entryRequest: appEntryRequest }) => ({
        url   : `/entry/${entryUuid}/request`,
        method: 'POST',
        body  : transformEntryRequestForApi(appEntryRequest)
      })
    }),
    updateEntryRequest: build.mutation({
      query: ({ entryUuid, entryRequestUuid, entryRequest: appEntryRequest }) => ({
        url   : `/entry/${entryUuid}/request/${entryRequestUuid}`,
        method: 'PUT',
        body  : transformEntryRequestForApi(appEntryRequest)
      })
    }),
    deleteEntryRequest: build.mutation({
      query: ({ entryUuid, entryRequestUuid }) => ({
        url   : `/entry/${entryUuid}/request/${entryRequestUuid}`,
        method: 'DELETE',
      })
    }),
    applyEntryRequest: build.mutation({
      query: ({ entryUuid, entryRequestUuid }) => ({
        url   : `/entry/${entryUuid}/request/${entryRequestUuid}/apply`,
        method: 'POST'
      })
    }),
    // NOTE: delete と reject は意味が違うので一旦別に分ける。
    rejectEntryRequest: build.mutation({
      query: ({ entryUuid, entryRequestUuid }) => ({
        url   : `/entry/${entryUuid}/request/${entryRequestUuid}`,
        method: 'DELETE',
      })
    })
  })
});

export const {
  useGetEntryListQuery,
  useLazyGetEntryListQuery,
  useGetEntryQuery,
  useLazyGetEntryQuery,
  useCheckUpdateTokenQuery,
  useCreateEntryMutation,
  useUpdateEntryMutation,
  useDeleteEntryMutation,
  useValidateEntryMutation,
  useSubmitEntryMutation,
  useCreateEntryCommentMutation,
  useUpdateEntryCommentMutation,
  useDeleteEntryCommentMutation,
  useGetEntryFileQuery,
  useDownlaodEntryFileMutation,
  useUploadEntryFileMutation,
  useDeleteEntryFileMutation,
  useCreateEntryRequestMutation,
  useUpdateEntryRequestMutation,
  useDeleteEntryRequestMutation,
  useRejectEntryRequestMutation,
  useApplyEntryRequestMutation
} = entryApi;
