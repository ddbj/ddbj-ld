// APIとの連携なしに各操作ができるかどうかを司るフラグ
const isDummy = true
const isDummyAdmin = true

const baseURLApi = "https://ddbj.nig.ac.jp"

// 認証系API
const loginApi = baseURLApi + "/auth/{code}/login"
const refreshAccessTokenApi = baseURLApi + "/auth/{account-uuid}/refresh"

const baseURLClient = "https://ddbj.nig.ac.jp"
const redirectUrl = baseURLClient + "/authorize"

const baseURLOpenAM = "https://accounts.ddbj.nig.ac.jp"
const authorizeUrl = baseURLOpenAM + "/openam/oauth2/authorize"

// プロジェクト系API
const createProjectApi = baseURLApi + "/view/project"
const getProjectApi = baseURLApi + "/view/project/{project-id}"
const beginEditProjectApi = baseURLApi + "/view/project/{project-id}/draft"
const postDraftMetadataApi = baseURLApi + "/view/project/{project-id}/draft/metadata"
const previewDraftMetadataApi = baseURLApi + "/view/project/{project-id}/draft/preview"
const requestPublishProjectApi = baseURLApi + "/view/project/{project-id}/draft/publish"
const getMyProjectsApi = baseURLApi + "/view/project/me"

// 権限付与API
const createAccessGrantApi = baseURLApi + "/view/access-grant/project/{project-id}"
const listAccessGrantApi   = baseURLApi + "/view/access-grant/project/{project-id}"
const deleteAccessGrantApi = baseURLApi + "/view/access-grant/token/{token}"
const updateAccessGrantApi = baseURLApi + "/view/access-grant/token/{token}"
const accessProjectPreviewDataApi = baseURLApi + "/view/access-grant/token/{token}/preview"

// 検索系API
const searchApi = baseURLApi + "/view/search"
const getSettingApi = baseURLApi + "/view/search/setting"

// ファイル系API
const preUploadApi = baseURLApi + "/view/project/{project-id}/file/{file-type}/{file-name}"
const uploadApi = baseURLApi + "/view/project/{project-id}/file/{file-type}/{file-name}/{token}"
const downloadApi = baseURLApi + "/view/project/{project-id}/file/{file-type}/{file-name}"
const deleteApi = baseURLApi + "/view/project/{project-id}/file/{file-type}/{file-name}"

// Editor URL
const editorUrl ="http://www.kazusa.or.jp/komics/software/MBEditor/"

const elasticsearchUrl = "http://localhost:9200"
const resourceApi = baseURLApi + "/resource"

const getEntriesApi = baseURLApi + "/entry"
const createEntryApi = baseURLApi + "/entry"
const deleteEntryApi = baseURLApi + "/entry/{entry_uuid}"
const getEntryInformationApi = baseURLApi + "/entry/{entry_uuid}"
const postCommentApi = baseURLApi + "/entry/{entry_uuid}/comment"

export default {
    isDummy,
    isDummyAdmin,
    baseURLClient,
    baseURLApi,
    loginApi,
    refreshAccessTokenApi,
    createProjectApi,
    getProjectApi,
    beginEditProjectApi,
    postDraftMetadataApi,
    previewDraftMetadataApi,
    requestPublishProjectApi,
    getMyProjectsApi,
    createAccessGrantApi,
    listAccessGrantApi,
    deleteAccessGrantApi,
    updateAccessGrantApi,
    accessProjectPreviewDataApi,
    searchApi,
    getSettingApi,
    preUploadApi,
    uploadApi,
    downloadApi,
    deleteApi,
    editorUrl,
    elasticsearchUrl,
    resourceApi,
    getEntriesApi,
    createEntryApi,
    deleteEntryApi,
    getEntryInformationApi,
    postCommentApi,
    remote: "https://flatlogic-node-backend.herokuapp.com",
    isBackend: process.env.REACT_APP_BACKEND,
    auth: {
        // TODO テンプレートに元からあった項目、削除予定
        email: 'admin@flatlogic.com',
        // TODO テンプレートに元からあった項目、削除予定
        password: 'password',
        // 認証で使用するIdP、OpenAMの設定情報
        // 下記２つを:でつなげてbase64encodeしてAuthorizationヘッダにつける
        clientId: 'entry_client',
        // 増やせば要求する情報の範囲を指定、複数ある場合はスペースか%20で指定
        scope: 'mail uid',
        // リダイレクトされる先
        redirectUrl,
        authorizeUrl
    },
    app: {
        colors: {
            dark: "#002B49",
            light: "#FFFFFF",
            sea: "#004472",
            sky: "#E9EBEF",
            wave: "#D1E7F6",
            rain: "#CCDDE9",
            middle: "#D7DFE6",
            black: "#13191D",
            salat: "#21AE8C",
        },
    }
};