const GET_ENTRIES  = 'ENTRY/GET_ENTRIES'
const SET_ENTRIES  = 'ENTRY/SET_ENTRIES'
const CREATE_ENTRY = 'ENTRY/CREATE_ENTRY'
const DELETE_ENTRY = 'ENTRY/DELETE_ENTRY'
const GET_ENTRY_INFO = 'ENTRY/GET_ENTRY_INFO'
const SET_CURRENT_ENTRY = 'ENTRY/SET_CURRENT_ENTRY'
const POST_COMMENT = 'ENTRY/POST_COMMENT'
const EDIT_COMMENT = 'ENTRY/EDIT_COMMENT'
const DELETE_COMMENT = 'ENTRY/DELETE_COMMENT'
const UPDATE_FILE = 'ENTRY/UPDATE_FILE'
const DOWNLOAD_FILE = 'ENTRY/DOWNLOAD_FILE'
const VALIDATE_METADATA = 'ENTRY/VALIDATE_METADATA'
const SUBMIT_ENTRY = 'ENTRY/SUBMIT_ENTRY'
const DELETE_FILE = 'ENTRY/DELETE_FILE'
const CREATE_REQUEST = 'ENTRY/CREATE_REQUEST'
const EDIT_REQUEST = 'ENTRY/EDIT_REQUEST'
const CANCEL_REQUEST = 'ENTRY/CANCEL_REQUEST'
const APPLY_REQUEST = 'ENTRY/APPLY_REQUEST'

const getEntries = (history, setLoading) => ({
    type: GET_ENTRIES,
    payload: {history, setLoading}
})

const setEntries = (entries) => ({
    type: SET_ENTRIES,
    payload: {entries}
})

const createEntry = (history, type, setLoading) => ({
    type: CREATE_ENTRY,
    payload: {history, type, setLoading}
})

const deleteEntry = (history, entryUUID, updateToken, setLoading) => ({
    type: DELETE_ENTRY,
    payload: {history, entryUUID, updateToken, setLoading}
})

const getEntryInfo = (history, uuid, setLoading) => ({
    type: GET_ENTRY_INFO,
    payload: {history, uuid, setLoading}
})

const setCurrentEntry = (currentEntry) => ({
    type: SET_CURRENT_ENTRY,
    payload: {currentEntry}
})

const postComment = (history, entryUUID, updateToken, comment, curator, setLoading) => ({
    type: POST_COMMENT,
    payload: {history, entryUUID, updateToken, comment, curator, setLoading}
})

const editComment = (history, entryUUID, updateToken, commentUUID, comment, curator, setLoading) => ({
    type: EDIT_COMMENT,
    payload: {history, entryUUID, updateToken, commentUUID, comment, curator, setLoading}
})

const deleteComment = (history, entryUUID, updateToken, commentUUID, setLoading) => ({
    type: DELETE_COMMENT,
    payload: {history, entryUUID, updateToken, commentUUID, setLoading}
})

const updateFile = (history, entryUUID, updateToken, type, name, file) => ({
    type: UPDATE_FILE,
    payload: {history, entryUUID, updateToken, type, name, file}
})

const downloadFile = (history, entryUUID, type, name) => ({
    type: DOWNLOAD_FILE,
    payload: {history, entryUUID, type, name}
})

const validateMetadata = (history, entryUUID, updateToken, setLoading) => ({
    type: VALIDATE_METADATA,
    payload: {history, entryUUID, updateToken, setLoading}
})

const submitEntry = (history, entryUUID, updateToken, setLoading) => ({
    type: SUBMIT_ENTRY,
    payload: {history, entryUUID, updateToken, setLoading}
})

const deleteFile = (history, entryUUID, updateToken, fileType, fileName, setLoading) => ({
    type: DELETE_FILE,
    payload: {history, entryUUID, updateToken, fileType, fileName, setLoading}
})

const createRequest = (history, entryUUID, updateToken, type, comment, setLoading) => ({
    type: CREATE_REQUEST,
    payload: {history, entryUUID, updateToken, type, comment, setLoading}
})

const editRequest = (history, entryUUID, updateToken, requestUUID, comment, setLoading) => ({
    type: EDIT_REQUEST,
    payload: {history, entryUUID, updateToken, requestUUID, comment, setLoading}
})

const cancelRequest = (history, entryUUID, updateToken, requestUUID, setLoading) => ({
    type: CANCEL_REQUEST,
    payload: {history, entryUUID, updateToken, requestUUID, setLoading}
})

const applyRequest = (history, entryUUID, updateToken, requestUUID, setLoading) => ({
    type: APPLY_REQUEST,
    payload: {history, entryUUID, updateToken, requestUUID, setLoading}
})

export {
    GET_ENTRIES, getEntries,
    SET_ENTRIES, setEntries,
    CREATE_ENTRY, createEntry,
    DELETE_ENTRY, deleteEntry,
    GET_ENTRY_INFO, getEntryInfo,
    SET_CURRENT_ENTRY, setCurrentEntry,
    POST_COMMENT, postComment,
    EDIT_COMMENT, editComment,
    DELETE_COMMENT, deleteComment,
    UPDATE_FILE, updateFile,
    DOWNLOAD_FILE, downloadFile,
    VALIDATE_METADATA, validateMetadata,
    SUBMIT_ENTRY, submitEntry,
    DELETE_FILE, deleteFile,
    CREATE_REQUEST, createRequest,
    EDIT_REQUEST, editRequest,
    CANCEL_REQUEST, cancelRequest,
    APPLY_REQUEST, applyRequest,
}