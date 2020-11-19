export const SET_PUBLISHED_PROJECT = 'PROJECT/SET_PUBLISHED_PROJECT'

export const setPublishedProjectBook = projectBook => ({
    type: SET_PUBLISHED_PROJECT,
    payload: {projectBook}
})

export const SET_DRAFT_PROJECT = 'PROJECT/SET_DRAFT_PROJECT'

export const setDraftProjectBook = draftMetadata => ({
    type: SET_DRAFT_PROJECT,
    payload: {draftMetadata}
})

export const FETCH_PUBLISHED_PROJECT = 'PROJECT/FETCH_PUBLISHED_PROJECT'

export const fetchPublishedProjectBook = id => ({
    type: FETCH_PUBLISHED_PROJECT,
    payload: {id}
})

export const FETCH_DRAFT_PROJECT = 'PROJECT/FETCH_DRAFT_PROJECT'

export const fetchDraftProjectBook = id => ({
    type: FETCH_DRAFT_PROJECT,
    payload: {id}
})

// TODO ここから上はデモ版なので見直す
const CREATE_PROJECT = 'PROJECT/CREATE_PROJECT'
const GET_PROJECT = 'PROJECT/GET_PROJECT'
const SET_CURRENT_PROJECT_VIEW = 'PROJECT/SET_CURRENT_PROJECT_VIEW'
const LIST_PROJECT_MEMBER = 'PROJECT/LIST_PROJECT_MEMBER'
const INVITE_MEMBER_AS_EDITOR = 'PROJECT/INVITE_MEMBER_AS_EDITOR'
const SUSPEND_MEMBER = 'PROJECT/SUSPEND_MEMBER'
const ACTIVE_MEMBER = 'PROJECT/ACTIVE_MEMBER'
const DELETE_MEMBER = 'PROJECT/DELETE_MEMBER'
const CREATE_ACCESS_GRANT = 'PROJECT/CREATE_ACCESS_GRANT'
const DELETE_ACCESS_GRANT = 'PROJECT/DELETE_ACCESS_GRANT'
const UPDATE_ACCESS_GRANT = 'PROJECT/UPDATE_ACCESS_GRANT'
const LIST_ACCESS_GRANT = 'PROJECT/LIST_ACCESS_GRANT'
const ACCESS_PROJECT_VIEW_DATA = 'PROJECT/ACCESS_PROJECT_VIEW_DATA'
const BEGIN_EDIT_PROJECT = 'PROJECT/BEGIN_EDIT_PROJECT'
const REVERT_EDIT_PROJECT = 'PROJECT/REVERT_EDIT_PROJECT'
const GET_DRAFT_METADATA = 'PROJECT/GET_DRAFT_METADATA'
const POST_DRAFT_METADATA = 'PROJECT/POST_DRAFT_METADATA'
const PREVIEW_DRAFT_METADATA = 'PROJECT/PREVIEW_DRAFT_METADATA'
const REQUEST_PUBLISH_PROJECT = 'PROJECT/REQUEST_PUBLISH_PROJECT'
const DELETE_PROJECT = 'PROJECT/DELETE_PROJECT'
const GET_METADATA = 'PROJECT/GET_METADATA'

export {
    CREATE_PROJECT,
    GET_PROJECT,
    SET_CURRENT_PROJECT_VIEW,
    LIST_PROJECT_MEMBER,
    INVITE_MEMBER_AS_EDITOR,
    SUSPEND_MEMBER,
    ACTIVE_MEMBER,
    DELETE_MEMBER,
    CREATE_ACCESS_GRANT,
    DELETE_ACCESS_GRANT,
    UPDATE_ACCESS_GRANT,
    LIST_ACCESS_GRANT,
    ACCESS_PROJECT_VIEW_DATA,
    BEGIN_EDIT_PROJECT,
    REVERT_EDIT_PROJECT,
    GET_DRAFT_METADATA,
    POST_DRAFT_METADATA,
    PREVIEW_DRAFT_METADATA,
    REQUEST_PUBLISH_PROJECT,
    DELETE_PROJECT,
    GET_METADATA
}

const createProject = (history, setLoading) => ({
    type: CREATE_PROJECT,
    payload: {history, setLoading}
})

const getProject = (projectId, history = null) => ({
    type: GET_PROJECT,
    payload: {projectId, history}
})

const setCurrentProjectView = (currentProjectView) => ({
    type: SET_CURRENT_PROJECT_VIEW,
    payload: {currentProjectView}
})

const listProjectMember = (projectId) => ({
    type: LIST_PROJECT_MEMBER,
    payload: {projectId}
})

const inviteMemberAsEditor = (projectId, accountUuid) => ({
    type: INVITE_MEMBER_AS_EDITOR,
    payload: {projectId, accountUuid}
})

const suspendMember = (projectId, accountUuid) => ({
    type: SUSPEND_MEMBER,
    payload: {projectId, accountUuid}
})

const activateMember = (projectId, accountUuid) => ({
    type: ACTIVE_MEMBER,
    payload: {projectId, accountUuid}
})

const deleteMember = (projectId, accountUuid) => ({
    type: DELETE_MEMBER,
    payload: {projectId, accountUuid}
})

const createAccessGrant = (projectId, label, expireDate) => ({
    type: CREATE_ACCESS_GRANT,
    payload: {projectId, label, expireDate}
})

const deleteAccessGrant = (projectId, token) => ({
    type: DELETE_ACCESS_GRANT,
    payload: {projectId, token}
})

const updateAccessGrant = (projectId, token, label, expireDate) => ({
    type: UPDATE_ACCESS_GRANT,
    payload: {projectId, token, label, expireDate}
})

const listAccessGrant = (projectId) => ({
    type: LIST_ACCESS_GRANT,
    payload: {projectId}
})

const accessProjectPreviewData = (projectId, token, history) => ({
    type: ACCESS_PROJECT_VIEW_DATA,
    payload: {projectId, token, history}
})

const beginEditProject = (projectId, history) => ({
    type: BEGIN_EDIT_PROJECT,
    payload: {projectId, history}
})

const revertEditProject = (projectId, history) => ({
    type: REVERT_EDIT_PROJECT,
    payload: {projectId, history}
})

const getDraftMetadata = (projectId) => ({
    type: GET_DRAFT_METADATA,
    payload: {projectId}
})

const postDraftMetadata = (projectId, metadata, setIsLoading) => ({
    type: POST_DRAFT_METADATA,
    payload: {projectId, metadata, setIsLoading}
})

const previewDraftMetadata = (projectId) => ({
    type: PREVIEW_DRAFT_METADATA,
    payload: {projectId}
})

const requestPublishProject = (projectId, setVisible, setIsLoading) => ({
    type: REQUEST_PUBLISH_PROJECT,
    payload: {projectId, setVisible, setIsLoading}
})

const deleteProject = (projectId) => ({
    type: DELETE_PROJECT,
    payload: {projectId}
})

// TODO 画面に必要なかったら消す
const getMetadata = (projectId, metadataId) => ({
    type: GET_METADATA,
    payload: {projectId, metadataId}
})


export {
    createProject,
    getProject,
    setCurrentProjectView,
    listProjectMember,
    inviteMemberAsEditor,
    suspendMember,
    activateMember,
    deleteMember,
    createAccessGrant,
    deleteAccessGrant,
    updateAccessGrant,
    listAccessGrant,
    accessProjectPreviewData,
    beginEditProject,
    revertEditProject,
    getDraftMetadata,
    postDraftMetadata,
    previewDraftMetadata,
    requestPublishProject,
    deleteProject,
    getMetadata
}