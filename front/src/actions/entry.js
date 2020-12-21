const GET_ENTRIES  = 'ENTRY/GET_ENTRIES'
const SET_ENTRIES  = 'ENTRY/SET_ENTRIES'
const CREATE_ENTRY = 'ENTRY/CREATE_ENTRY'
const DELETE_ENTRY = 'ENTRY/DELETE_ENTRY'
const GET_ENTRY_INFORMATION = 'ENTRY/GET_ENTRY_INFORMATION'
const SET_CURRENT_ENTRY = 'ENTRY/SET_CURRENT_ENTRY'
const POST_COMMENT = 'ENTRY/POST_COMMENT'
const EDIT_COMMENT = 'ENTRY/EDIT_COMMENT'
const DELETE_COMMENT = 'ENTRY/DELETE_COMMENT'
const UPDATE_FILE = 'ENTRY/UPDATE_FILE'
const DOWNLOAD_FILE = 'ENTRY/DOWNLOAD_FILE'
const VALIDATE_METADATA = 'ENTRY/VALIDATE_METADATA'
const SUBMIT_ENTRY = 'ENTRY/SUBMIT_ENTRY'

const getEntries = (history) => ({
    type: GET_ENTRIES,
    payload: {history}
})

const setEntries = (entries) => ({
    type: SET_ENTRIES,
    payload: {entries}
})

const createEntry = (history, type, setLoading) => ({
    type: CREATE_ENTRY,
    payload: {history, type, setLoading}
})

const deleteEntry = (history, uuid, setLoading) => ({
    type: DELETE_ENTRY,
    payload: {history, uuid, setLoading}
})

const getEntryInformation = (history, uuid, setLoading) => ({
    type: GET_ENTRY_INFORMATION,
    payload: {history, uuid, setLoading}
})

const setCurrentEntry = (currentEntry) => ({
    type: SET_CURRENT_ENTRY,
    payload: {currentEntry}
})

const postComment = (history, entryUUID, comment, setLoading) => ({
    type: POST_COMMENT,
    payload: {history, entryUUID, comment, setLoading}
})

const editComment = (history, entryUUID, commentUUID, comment, setLoading) => ({
    type: EDIT_COMMENT,
    payload: {history, entryUUID, commentUUID, comment, setLoading}
})

const deleteComment = (history, entryUUID, commentUUID, setLoading) => ({
    type: DELETE_COMMENT,
    payload: {history, entryUUID, commentUUID, setLoading}
})

const updateFile = (history, entryUUID, type, name, file) => ({
    type: UPDATE_FILE,
    payload: {history, entryUUID, type, name, file}
})

const downloadFile = (history, entryUUID, type, name) => ({
    type: DOWNLOAD_FILE,
    payload: {history, entryUUID, type, name}
})

const validateMetadata = (history, entryUUID, setLoading) => ({
    type: VALIDATE_METADATA,
    payload: {history, entryUUID, setLoading}
})

const submitEntry = (history, entryUUID, setLoading) => ({
    type: SUBMIT_ENTRY,
    payload: {history, entryUUID, setLoading}
})

export {
    GET_ENTRIES, getEntries,
    SET_ENTRIES, setEntries,
    CREATE_ENTRY, createEntry,
    DELETE_ENTRY, deleteEntry,
    GET_ENTRY_INFORMATION, getEntryInformation,
    SET_CURRENT_ENTRY, setCurrentEntry,
    POST_COMMENT, postComment,
    EDIT_COMMENT, editComment,
    DELETE_COMMENT, deleteComment,
    UPDATE_FILE, updateFile,
    DOWNLOAD_FILE, downloadFile,
    VALIDATE_METADATA, validateMetadata,
    SUBMIT_ENTRY, submitEntry,
}