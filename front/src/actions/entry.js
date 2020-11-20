const GET_ENTRIES  = 'ENTRY/GET_ENTRIES'
const SET_ENTRIES  = 'ENTRY/SET_ENTRIES'
const CREATE_ENTRY = 'ENTRY/CREATE_ENTRY'
const DELETE_ENTRY = 'ENTRY/DELETE_ENTRY'
const GET_ENTRY_INFORMATION = 'ENTRY/GET_ENTRY_INFORMATION'
const SET_CURRENT_ENTRY = 'ENTRY/SET_CURRENT_ENTRY'

const getEntries = (history) => ({
    type: GET_ENTRIES,
    payload: {history}
})

const setEntries = (entries) => ({
    type: SET_ENTRIES,
    payload: {entries}
})

const createEntry = (history, title, description, setLoading) => ({
    type: CREATE_ENTRY,
    payload: {history, title, description, setLoading}
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

export {
    GET_ENTRIES, getEntries,
    SET_ENTRIES, setEntries,
    CREATE_ENTRY, createEntry,
    DELETE_ENTRY, deleteEntry,
    GET_ENTRY_INFORMATION, getEntryInformation,
    SET_CURRENT_ENTRY, setCurrentEntry
}